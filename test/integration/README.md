# Running the Liferay Faces Portal Integration Tests

The Liferay Faces Portal integration tests can be run from an IDE (such as Eclipse) or the command line. The test
framework expects that the tests have already been deployed to a running portal instance and have already been added to
the appropriate pages.

Before running the tests from the command line, you must navigate into the `test/integration` directory:

	cd test/integration

The tests can be activated by using the `selenium` maven profile. To run all the tests (PhantomJS and Liferay are the
default browser and container properties respectively):

	mvn verify -P selenium

Different browsers can be activated via the `chrome`, `firefox`, `jbrowser`, and `htmlunit` maven profiles. For example,
to run the tests on Firefox:

	mvn verify -P selenium,firefox

**Note:** HTMLUnit and [JBrowser](https://github.com/MachinePublishers/jBrowserDriver) may fail to open web pages with
complex JavaScript due to their experimental/buggy JavaScript support. PhantomJS is recommended for testing complex
pages in a headless environment. Chrome (or the slightly slower Firefox) is recommended for testing complex pages in a
normal desktop environment. See the root `pom.xml` file dependencies section for the required versions of each browser.

Single tests and groups of tests can be selected via the the `it.test` property. The `it.test` property uses wildcards
to select tests from their fully qualified class names. For example, to run only the PrimeFaces Users Portlet test:

	mvn verify -P selenium -Dit.test=\*PrimeFacesUsers\*

Likewise, `-Dit.test=\*JSF\*` would run only the JSF portlet tests.

The `integration.port` property controls which port the browser will navigate to in order to run the tests. For example,
if the portal is running on port `4000`, then the following command would be needed to test the portlets:

    mvn verify -P selenium -Dintegration.port=4000

All of the above properties and profiles can be combined to run tests in more complex scenarios. Here are some examples:

- Run the tests on Firefox against a Liferay Portal instance:

		mvn verify -P selenium,firefox

- Run the PrimeFaces Users Portlet test against a Liferay Portal instance on Chrome:

		mvn verify -P selenium -Dit.test=\*PrimeFacesUsers\*

- Run the JSF Showcase testers:

        mvn verify -P selenium-portal-showcase

**Note:** In order to run the Showcase testers, you will need to build the relevant test jars.

#### Building the JSF Showcase test jar:

1. Obtain the version of the JSF Showcase that needs to be built:

        LIFERAY_FACES_SHOWCASE_VERSION=$(mvn org.codehaus.mojo:exec-maven-plugin:1.2.1:exec -Dexec.executable="echo" \
            -q --non-recursive -Dexec.args='${liferay.faces.showcase.version}' | \
            sed 's/[.][0-9][0-9]*[.][0-9][0-9]*-SNAPSHOT/.x/')

2. Navigate to the Liferay Faces Showcase directory:

        cd $LIFERAY_PROJECTS_HOME/liferay-faces-showcase/jsf-showcase-webapp

3. Check out the correct version:

        git checkout $LIFERAY_FACES_SHOWCASE_VERSION

4. Build the test jar:

        mvn clean install -P selenium -Dit.test=none -DfailIfNoTests=false
