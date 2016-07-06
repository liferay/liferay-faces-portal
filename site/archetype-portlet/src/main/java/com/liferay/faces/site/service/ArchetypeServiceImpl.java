/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.site.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.liferay.faces.site.dto.Archetype;
import com.liferay.faces.site.dto.JsfVersion;
import com.liferay.faces.site.dto.LiferayVersion;
import com.liferay.faces.site.dto.Suite;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean(name = "archetypeService", eager = true)
@ApplicationScoped
public class ArchetypeServiceImpl implements ArchetypeService {

	private static final Logger logger = LoggerFactory.getLogger(ArchetypeServiceImpl.class);
	
	private List<LiferayVersion> liferayVersions;
	private List<JsfVersion> jsfVersions;
	private List<Suite> suites;
	private List<Archetype> archetypes;
	
	boolean snapshot = false;
	
	// my favorite url for now:
	// https://oss.sonatype.org/content/repositories/snapshots/com/liferay/faces/archetype/
	
	// http://search.maven.org/remotecontent?filepath=com/liferay/faces/archetype/com.liferay.faces.archetype.richfaces.portlet/5.0.0/com.liferay.faces.archetype.richfaces.portlet-5.0.0.jar
	// https://repo1.maven.org/maven2/com/liferay/faces/archetype/com.liferay.faces.archetype.alloy.portlet/5.0.0/com.liferay.faces.archetype.alloy.portlet-5.0.0.jar
	
	String archetypeContext = "https://repo1.maven.org/maven2/com/liferay/faces/archetype/";
	String archetypeGenerate = "mvn archetype:generate -DarchetypeGroupId=com.liferay.faces.archetype -DarchetypeArtifactId=com.liferay.faces.archetype.SUITE.portlet -DarchetypeVersion=VERSION -DgroupId=com.mycompany -DartifactId=com.mycompany.my.SUITE.portlet";
	
	public void init() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletConfig portletConfig = (PortletConfig) portletRequest.getAttribute("javax.portlet.config");
		Enumeration<String> enumeratedNames = portletConfig.getInitParameterNames();
		
		ArrayList<String> namesList = new ArrayList<String>();
		
		HashMap<String,String> extVersionsMap = new HashMap<String,String>();
		
		HashMap<String,String> liferayVersionsMap = new HashMap<String,String>();
		HashMap<String,String> jsfVersionsMap = new HashMap<String,String>();
		
		HashMap<String,String> extLiferayMap = new HashMap<String,String>();
		HashMap<String,String> extJsfMap = new HashMap<String,String>();
		
		while (enumeratedNames.hasMoreElements()) {
			String key = (String) enumeratedNames.nextElement();
			if (key.matches("snapshot")) {
				if ("true".equals(portletConfig.getInitParameter(key))) {
					snapshot = true;
				}
			}
			if (key.matches("liferay-\\d+..*")) {
				namesList.add(key);
				String[] versions = key.split(" ");
				
				String extVersion = portletConfig.getInitParameter(key);
				String liferayVersion = versions[0].substring("liferay-".length());
				String jsfVersion = versions[1];
				
				String qualifiedExt = extVersion + ((snapshot) ? "-SNAPSHOT" : "");
				
				extVersionsMap.put(qualifiedExt, "1");
				extLiferayMap.put(qualifiedExt, liferayVersion);
				extJsfMap.put(qualifiedExt, jsfVersion);
				
				liferayVersionsMap.put(liferayVersion, "1");
				jsfVersionsMap.put(jsfVersion, "1");
				
			}
		}

		String[] names = namesList.toArray(new String[namesList.size()]);
		Arrays.sort(names, Collections.reverseOrder());
		
		if (liferayVersions == null) {
			
			liferayVersions = new ArrayList<LiferayVersion>();
			
			String[] liferays = liferayVersionsMap.keySet().toArray(new String[liferayVersionsMap.keySet().size()]);
			Arrays.sort(liferays, Collections.reverseOrder());
			long id = 0;
			for (String liferayVersion : liferays) {
				liferayVersions.add(new LiferayVersion(id, liferayVersion, liferayVersion));
				id++;
			}
		}
		
		if (jsfVersions == null) {
			
			jsfVersions = new ArrayList<JsfVersion>();
			
			String[] jsfs = jsfVersionsMap.keySet().toArray(new String[jsfVersionsMap.keySet().size()]);
			Arrays.sort(jsfs, Collections.reverseOrder());
			long id = 0;
			for (String jsfVersion : jsfs) {
				jsfVersions.add(new JsfVersion(id, jsfVersion, jsfVersion));
				id++;
			}
		}
		
//		LinkedHashMap<String, String[]> exts = new LinkedHashMap<String, String[]>();
//		for (String key : names) {
//			String[] versions = portletConfig.getInitParameter(key).split(" ");
//			exts.put(key, versions);
//		}
		
		HashMap<String,String> suiteMap = new HashMap<String,String>();
		suites = new ArrayList<Suite>();
		long suiteId = 0;
		
		archetypes = new ArrayList<Archetype>();
		long archetypeId = 0;
		
		String nextUrl = archetypeContext;
		String groupId = "com.liferay.faces.archetype";
		
		if (snapshot) {
			archetypeContext = "https://oss.sonatype.org/content/repositories/snapshots/com/liferay/faces/archetype/";
		}
		logger.info("init: using archetypeContext = " + archetypeContext);
		
		Document suitesDocument;
		try {
			suitesDocument = Jsoup.connect(archetypeContext).get();
//			logger.info("Jsoup: " + suitesDocument.html());
			for (Element potentialSuite : suitesDocument.select("td,pre").select("a")) {
//				logger.info("init: potentialSuite = " + potentialSuite.toString());
				if (potentialSuite.attr("href").contains(groupId)) {
//					logger.info("init: " + potentialSuite.attr("href").substring(archetypeContext.length()).replaceAll("/", ""));
					
					String suite;
					
					if (potentialSuite.attr("href").contains(archetypeContext)) {
						suite = potentialSuite.attr("href")
							.substring(archetypeContext.length())
							.replaceAll("/", "")
							.substring("com.liferay.faces.archetype.".length())
							.replace(".portlet", "");
						nextUrl = potentialSuite.attr("href");
					} else {
						suite = potentialSuite.attr("href")
							.replaceAll("/", "")
							.substring("com.liferay.faces.archetype.".length())
							.replace(".portlet", "");
						nextUrl = archetypeContext + potentialSuite.attr("href");
					}
					
					suiteMap.put(suite, "1");
					
					Document versionsDocument = Jsoup.connect(nextUrl).get();
//					logger.info("init: " + versionsDocument.html());
					for (Element potentialVersion : versionsDocument.select("td,pre").select("a")) {
//						logger.info("init: potentialVersion = " + potentialVersion.toString());
						
						String version;
						
						if (potentialVersion.attr("href").contains(potentialSuite.attr("href"))) {
							version = potentialVersion.attr("href").substring(potentialSuite.attr("href").length()).replaceAll("/", "");
						} else {
							version = potentialVersion.attr("href").replaceAll("/", "");
						}
						

						// check this potentialVersion against the exts in the init-params ...
						if (extVersionsMap.containsKey(version)) {
							logger.info("init: version = " + version);

							String extVersion = version;
							String jsfVersion = extJsfMap.get(version);
							String liferayVersion = extLiferayMap.get(version);
							String mavenCommand = archetypeGenerate.replace("VERSION", version).replaceAll("SUITE", suite);

							if (potentialVersion.attr("href").contains(potentialSuite.attr("href"))) {
								nextUrl = potentialVersion.attr("href");
							} else {
								nextUrl = archetypeContext + potentialSuite.attr("href") + potentialVersion.attr("href");
							}
							
							Document filesDocument = Jsoup.connect(nextUrl).get();

							String fileName = "";
							URL url = null;

							for (Element potentialFile : filesDocument.select("td,pre").select("a")) {
//								logger.info("init: potentialFile = " + potentialFile.toString());

								if (potentialFile.attr("href").matches("..*.jar")) {
									
									if (potentialFile.attr("href").contains(potentialVersion.attr("href"))) {
										fileName = potentialFile.attr("href")
											.substring(potentialVersion.attr("href").length())
											.replaceAll("/", "");
										url = new URL(potentialFile.attr("href"));
									} else {
										fileName = potentialFile.attr("href").replaceAll("/", "");
										url = new URL(archetypeContext + potentialSuite.attr("href") + potentialVersion.attr("href") + potentialFile.attr("href"));
									}
								}
							}

							// get the model, properties, and dependencies for the archetype
							try {

								HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
								urlConnection.setRequestMethod("GET");
								urlConnection.setDoOutput(true);
								urlConnection.connect();

								File file = new File(fileName);

								FileOutputStream fileOutput = new FileOutputStream(file);
								InputStream inputStream = urlConnection.getInputStream();

								byte[] buffer = new byte[1024];
								int bufferLength = 0;

								while ((bufferLength = inputStream.read(buffer)) > 0) {
									fileOutput.write(buffer, 0, bufferLength);
								}
								fileOutput.close();

								JarFile jar = new JarFile(file);
								Enumeration<JarEntry> entries = jar.entries();
								while (entries.hasMoreElements()) {
									JarEntry entry = (JarEntry) entries.nextElement();
									if ("archetype-resources/pom.xml".equals(entry.getName())) {

										File pom = new File("pom.xml");
										// logger.info("init: pom.getCanonicalPath() = " + pom.getCanonicalPath());

										InputStream is = jar.getInputStream(entry);
										FileOutputStream fos = new FileOutputStream(pom);
										while (is.available() > 0) {
											fos.write(is.read());
										}
										fos.close();
										is.close();

										MavenXpp3Reader reader = new MavenXpp3Reader();
										Model model = null;
										String propertiesAndDependencies = "";
										try {
											model = reader.read(new FileReader(pom));
											// logger.info("init: fileName = " + fileName);
											// logger.info("init: model.getProperties() = " + model.getProperties());
											// logger.info("init: model.getDependencies() = " + model.getDependencies());

											List<String> lines = Files.readAllLines(
													Paths.get(pom.getCanonicalPath()), StandardCharsets.UTF_8);
											boolean inProperties = false;
											boolean inDependencies = false;
											for (String line : lines) {
												if (line.contains("<properties>")) {
													inProperties = true;
												}
												if (line.contains("<dependencies>")) {
													inDependencies = true;
												}
												if (inProperties || inDependencies) {
													// logger.info("init: " + line);
													propertiesAndDependencies += line;
													propertiesAndDependencies += "\n";
												}
												if (line.contains("</properties>")) {
													inProperties = false;
												}
												if (line.contains("</dependencies>")) {
													inDependencies = false;
												}
											}
										} catch (XmlPullParserException e) {
											e.printStackTrace();
										}

										// logger.info("init: propertiesAndDependencies = " + propertiesAndDependencies);
										logger.info("init: " + archetypeId + " " + liferayVersion + " " + jsfVersion
												+ " " + suite + " " + extVersion);
										archetypes.add(new Archetype(archetypeId, liferayVersion, jsfVersion, suite,
												extVersion, propertiesAndDependencies, model, mavenCommand));
										++archetypeId;
									}
								}
								jar.close();
								file.delete();

							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} // end if (extVersionsMap.containsKey(version)) ... this checks to see if the extVersion is in our list of init-params
					} //  end for (Element potentialVersion : versionsDocument.select("td a"))
				} // end if (potentialSuite.attr("href").contains(groupId))
	        } // for (Element potentialSuite : suitesDocument.select("td a"))
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for (Map.Entry<String, String> entry : suiteMap.entrySet()) {
			suites.add(new Suite(suiteId, entry.getKey()));
			++suiteId;
		}
	}
	
	@Override
	public List<Archetype> getArchetypes() {
		
		checkForUpdates();
		logger.info("getArchetypes: archetypes.size() = " + archetypes.size());
		return archetypes;
	}
	
	@Override
	public List<JsfVersion> getJsfVersions() {
		
		checkForUpdates();
		return jsfVersions;
	}
	
	@Override
	public List<LiferayVersion> getLiferayVersions() {
		
		checkForUpdates();
		return liferayVersions;
	}
	
	@Override
	public List<Suite> getSuites() {

		checkForUpdates();
		return suites;
	}
	
	public void checkForUpdates() {
		if (liferayVersions == null) { init(); }
		if (jsfVersions == null) { init(); }
		if (suites == null) { init(); }
		if (archetypes == null) { init(); }
	}

}
