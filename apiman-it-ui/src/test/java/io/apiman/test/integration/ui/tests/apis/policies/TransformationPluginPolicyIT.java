package io.apiman.test.integration.ui.tests.apis.policies;

import io.apiman.test.integration.base.policies.PolicyDefs;
import io.apiman.test.integration.ui.support.assertion.BeanAssert;
import io.apiman.test.integration.ui.support.selenide.pages.apis.detail.ApiPoliciesDetailPage;
import io.apiman.test.integration.ui.support.selenide.pages.policies.AddTransformationPolicyPage;
import org.junit.Before;
import org.junit.Test;

/**
 * @author opontes
 */
public class TransformationPluginPolicyIT extends AbstractApiPolicyIT {

    private AddTransformationPolicyPage addPolicyPage;

    @Before
    public void openPage(){
        addPolicyPage = policiesDetailPage.addPolicy(AddTransformationPolicyPage.class);
    }

    @Override
    protected String getDefinitionId() {
        return PolicyDefs.TRANSFORMATION_POLICY;
    }

    @Test
    public void canAddPolicy(){
        addPolicyPage
                .configure("XML", "XML")
                .addPolicy(ApiPoliciesDetailPage.class);
        assertPolicyPresent();
    }

    @Test
    public void canCancelPolicy(){
        addPolicyPage
                .configure("XML", "XML")
                .cancel(ApiPoliciesDetailPage.class);
        BeanAssert.assertNoPolicies(apiVersions);
    }
}
