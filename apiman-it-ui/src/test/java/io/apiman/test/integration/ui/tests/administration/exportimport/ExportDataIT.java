/*
 * Copyright 2016 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.test.integration.ui.tests.administration.exportimport;

import static io.apiman.test.integration.ui.support.selenide.SelenideUtils.open;

import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.base.administration.exportimport.AbstractExportDataTest;
import io.apiman.test.integration.ui.support.selenide.pages.administration.exportimport.ExportImportAdminPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;

/**
 * This test rely on one of following web drivers:
 * {@link io.apiman.test.integration.ui.support.browsers.ChromeWebDriverProvider}
 * {@link io.apiman.test.integration.ui.support.browsers.FirefoxWebDriverProvider}
 * Use web driver provider by setting system property 'selenide.browser'.
 * @author jkaspar
 */
public class ExportDataIT extends AbstractExportDataTest {

    private static final String EXPORTED_FILE_NAME = "api-manager-export.json";
    private static final String EXPORTED_FILE_PATH = SuiteProperties.getProperty(SuiteProperties.BROWSER_DOWNLOAD_DIR)
        + EXPORTED_FILE_NAME;
    private static final File EXPORTED_FILE = new File(EXPORTED_FILE_PATH);

    @Override
    protected String getExportedJson() throws InterruptedException, IOException {
        open(ExportImportAdminPage.class).exportAll();

        // Wait for download
        TimeUnit.SECONDS.sleep(Integer.valueOf(SuiteProperties.getProperty("apiman.test.delay")));

        return FileUtils.readFileToString(EXPORTED_FILE);
    }

    @AfterClass
    public static void removeDownloadedFile() throws Exception {
        if (!EXPORTED_FILE.delete()) {
            throw new Exception("Cannot delete file " + EXPORTED_FILE_PATH);
        }
    }
}
