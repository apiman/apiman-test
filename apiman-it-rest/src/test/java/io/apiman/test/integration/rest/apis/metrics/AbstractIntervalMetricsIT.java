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

package io.apiman.test.integration.rest.apis.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.apiman.manager.api.beans.metrics.HistogramBean;
import io.apiman.manager.api.beans.metrics.HistogramDataPoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jkaspar
 */
public abstract class AbstractIntervalMetricsIT extends AbstractMetricsIT {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractIntervalMetricsIT.class);

    protected static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    protected static SimpleDateFormat formatter;
    protected Date tenMinutesAfterRecording;

    /**
     * Get metrics from a 10 minutes interval divided into minutes.
     * @return HistogramBean of this metrics
     */
    public abstract HistogramBean<HistogramDataPoint> getMetrics();

    protected void recordMetricsIntoNextMinute() throws InterruptedException {
        // wait until next minute begin
        long millisWithinMinute = System.currentTimeMillis() % TimeUnit.MINUTES.toMillis(1);
        long waitFor = TimeUnit.MINUTES.toMillis(1) - millisWithinMinute;
        LOG.info(String.format("Waiting %d seconds until next minute begin.", TimeUnit.MILLISECONDS.toSeconds(waitFor)));
        Thread.sleep(waitFor);

        recordSuccessfulRequests(2);
        recordFailedRequests(5);
        TimeUnit.SECONDS.sleep(TIME_DELAY);
    }

    @BeforeClass
    public static void setUpFormatter() throws Exception {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Before
    public void setUp() throws Exception {
        calendar.setTime(beforeRecoding);
        calendar.add(Calendar.MINUTE, 9);
        tenMinutesAfterRecording = calendar.getTime();
    }

    @Test
    public void intervalIsDividedToCorrectNumberOfSubintervals() throws Exception {
        HistogramBean metrics = getMetrics();
        assertEquals("Unexpected number of subintervals", 10, metrics.getData().size());
    }

    @Test
    public void subintervalsAreConsecutive() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();

        Date prevDate = formatter.parse(metrics.getData().get(0).getLabel());
        for (int i = 1; i < metrics.getData().size(); i++) {
            Date date = formatter.parse(metrics.getData().get(i).getLabel());
            assertTrue("Unexpected label value", date.after(prevDate));
            prevDate = date;
        }
    }

    @Test
    public void firstSubintervalHasTheSameLabelValueAsTheBeginningOfTheRequiredInterval() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();
        Date firstSubinterval = formatter.parse(metrics.getData().get(0).getLabel());

        assertEquals("Unexpected label value of first subinterval",
                DateUtils.truncate(beforeRecoding, Calendar.MINUTE), firstSubinterval);
    }

    @Test
    public void lastSubintervalHasTheSameLabelValueAsTheEndOfTheRequiredInterval() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();
        int last = metrics.getData().size()-1;
        Date lastSubinterval = formatter.parse(metrics.getData().get(last).getLabel());

        assertEquals("Unexpected label value of first subinterval",
                DateUtils.truncate(tenMinutesAfterRecording, Calendar.MINUTE), lastSubinterval);
    }
}
