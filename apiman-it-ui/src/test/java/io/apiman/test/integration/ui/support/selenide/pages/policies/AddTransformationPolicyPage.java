package io.apiman.test.integration.ui.support.selenide.pages.policies;

import com.codeborne.selenide.SelenideElement;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author opontes
 */
public class AddTransformationPolicyPage extends AbstractAddPolicyPage{

    public SelenideElement clientDataFormat(){
        return $("select[name=\"root[clientFormat]\"");
    }

    public SelenideElement serverDataFormat(){
        return $("select[name=\"root[serverFormat]\"");
    }

    public AddTransformationPolicyPage configure(String client, String server){
        clientDataFormat().selectOptionByValue(client);
        serverDataFormat().selectOptionByValue(server);
        return (AddTransformationPolicyPage) thisPageObject();
    }

    @Override
    public AbstractAddPolicyPage selectPolicyType() {
        return policyType("Transformation Policy");
    }
}
