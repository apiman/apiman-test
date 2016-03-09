package io.apiman.test.integration.ui.support.selenide.pages.policies;

import com.codeborne.selenide.SelenideElement;
import io.apiman.test.integration.ui.support.selenide.layouts.AbstractAddPolicyPage;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author opontes
 */
public class AddURLRewritingPolicyPage extends AbstractAddPolicyPage {

    public SelenideElement regex(){
        return $("input[id='fromRegexp']");
    }

    public SelenideElement replacement(){
        return $("input[id='toReplacement']");
    }

    public SelenideElement processHeader(){
        return $("input[id='processHeaders']");
    }

    public SelenideElement processBody(){
        return $("input[id='processBody']");
    }

    public AddURLRewritingPolicyPage configure(
            String regex, String replacement, boolean processHeader, boolean processBody){
        regex().setValue(regex);
        replacement().setValue(replacement);
        if (processHeader) processHeader().click();
        if (processBody) processBody().click();
        return (AddURLRewritingPolicyPage) thisPageObject();
    }

    @Override
    public AbstractAddPolicyPage selectPolicyType() {
        return policyType("URL Rewriting Policy");
    }
}
