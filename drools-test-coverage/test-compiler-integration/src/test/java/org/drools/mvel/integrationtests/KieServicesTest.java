package org.drools.mvel.integrationtests;

import java.util.Collection;

import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.drools.compiler.kie.builder.impl.KieServicesImpl;
import org.drools.testcoverage.common.util.KieBaseTestConfiguration;
import org.drools.testcoverage.common.util.KieUtil;
import org.drools.testcoverage.common.util.TestParametersUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(Parameterized.class)
public class KieServicesTest {

    private final KieBaseTestConfiguration kieBaseTestConfiguration;

    public KieServicesTest(final KieBaseTestConfiguration kieBaseTestConfiguration) {
        this.kieBaseTestConfiguration = kieBaseTestConfiguration;
    }

    @Parameterized.Parameters(name = "KieBase type={0}")
    public static Collection<Object[]> getParameters() {
        return TestParametersUtil.getKieBaseCloudConfigurations(true);
    }
	
	private KieServices ks;

	@Before
	public void init() {
		ks = KieServices.Factory.get();
		(( KieServicesImpl ) ks).nullKieClasspathContainer();
		((KieServicesImpl) ks).nullAllContainerIds();
	}
	
	@After
	public void shutdown() {
		((KieServicesImpl) ks).nullKieClasspathContainer(); 
		((KieServicesImpl) ks).nullAllContainerIds();
	}
	
	@Test
	public void testGetKieClasspathIDs() {
		String myId = "myId";
		
		KieContainer c1 = ks.getKieClasspathContainer(myId);

        assertThat(ks.getKieClasspathContainer()).isEqualTo(c1);
        assertThat(ks.getKieClasspathContainer(myId)).isEqualTo(c1);
		try {
			ks.getKieClasspathContainer("invalid");
			fail("this is not the containerId for the global singleton.");
		} catch (IllegalStateException is) {
			// ok.
		}
	}
	
	@Test
	public void testNewKieClasspathIDs() {
		KieContainer c1 = ks.newKieClasspathContainer("id1");
		KieContainer c2 = ks.newKieClasspathContainer("id2");
		try {
			ks.newKieClasspathContainer("id2");
			fail("should not allow repeated container IDs.");
		} catch (IllegalStateException is) {
			// ok.
		}
	}
	
	@Test
	public void testNewKieContainerIDs() {
		ReleaseId releaseId = ks.newReleaseId("org.kie", "test-delete", "1.0.0");
        KieModule km = KieUtil.getKieModuleFromDrls(releaseId, kieBaseTestConfiguration, createDRL("ruleA"));

		KieContainer c1 = ks.newKieContainer("id1", releaseId);
		KieContainer c2 = ks.newKieClasspathContainer("id2");
		try {
			ks.newKieContainer("id2", releaseId);
			fail("should not allow repeated container IDs.");
		} catch (IllegalStateException is) {
			// ok.
		}
		try {
			ks.newKieClasspathContainer("id1");
			fail("should not allow repeated container IDs.");
		} catch (IllegalStateException is) {
			// ok.
		}
	}
	
	@Test
	public void testDisposeClearTheIDReference() {
		ReleaseId releaseId = ks.newReleaseId("org.kie", "test-delete", "1.0.0");
        KieModule km = KieUtil.getKieModuleFromDrls(releaseId, kieBaseTestConfiguration, createDRL("ruleA"));

		KieContainer c1 = ks.newKieContainer("id1", releaseId);
		try {
			ks.newKieClasspathContainer("id1");
			fail("should not allow repeated container IDs.");
		} catch (IllegalStateException is) {
			// ok.
		}
		
		(( KieContainerImpl ) c1).dispose();
		
		ks.newKieClasspathContainer("id1"); // now OK.
	}

    private String createDRL(String ruleName) {
        return "package org.kie.test\n" +
               "global java.util.List list\n" +
               "rule " + ruleName + "\n" +
               "when\n" +
               "then\n" +
               "list.add( drools.getRule().getName() );\n" +
               "end\n";
    }
}
