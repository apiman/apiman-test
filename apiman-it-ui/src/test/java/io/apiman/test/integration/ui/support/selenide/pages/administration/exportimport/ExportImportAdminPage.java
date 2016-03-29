package io.apiman.test.integration.ui.support.selenide.pages.administration.exportimport;

import static com.codeborne.selenide.Selenide.$;

import io.apiman.test.integration.ui.support.selenide.ByApiman;
import io.apiman.test.integration.ui.support.selenide.PageLocation;
import io.apiman.test.integration.ui.support.selenide.layouts.AdminPage;

import com.codeborne.selenide.SelenideElement;

/**
 * @author jkaspar
 */
@PageLocation("/apimanui/api-manager/admin/export")
public class ExportImportAdminPage extends AdminPage<ExportImportAdminPage> {

    public SelenideElement exportAllButton() {
        return $(ByApiman.i18n("button", "export-all"));
    }

    public ExportImportAdminPage exportAll() {
        exportAllButton().click();
        return this;
    }

    public SelenideElement uploadFileButton() {
        return $(ByApiman.i18n("button", "upload-file"));
    }

    public SelenideElement uploadFileInput() {
        return $(ByApiman.ngModel("importFile.name"));
    }
}
