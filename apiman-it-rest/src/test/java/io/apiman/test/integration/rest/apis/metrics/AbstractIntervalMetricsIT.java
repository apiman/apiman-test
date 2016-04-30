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

import io.apiman.test.integration.Suite;
import io.apiman.manager.api.beans.metrics.HistogramBean;
import io.apiman.manager.api.beans.metrics.HistogramDataPoint;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

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

    protected static DateTimeFormatter formatter;
    protected LocalDateTime tenMinutesAfterRecording;

    /**
     * Get metrics from a 10 minutes interval divided into minutes.
     * @return HistogramBean of this metrics
     */
    public abstract HistogramBean<HistogramDataPoint> getMetrics();

    protected void recordMetricsInFollowingMinute() throws InterruptedException {
        // wait until current minute expires
        long millisWithinMinute = System.currentTimeMillis() % TimeUnit.MINUTES.toMillis(1);
        long waitFor = TimeUnit.MINUTES.toMillis(1) - millisWithinMinute;
        Suite.waitFor(60 * 1000 - millisWithinMinute, "Waiting %d milliseconds until end of minute.");

        recordSuccessfulRequests(2);
        recordFailedRequests(5);
        Suite.waitForAction();
    }

    @BeforeClass
    public static void setUpFormatter() throws Exception {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @Before
    public void setUp() throws Exception {
        tenMinutesAfterRecording = afterRecording.plusMinutes(10);
    }

    @Test
    public void shouldHaveCorrectNumberOfSubintervals() throws Exception {
        HistogramBean metrics = getMetrics();

        double secondsBetween = Duration.between(
            beforeRecoding.truncatedTo(ChronoUnit.MINUTES), // truncate to get starting minute
            tenMinutesAfterRecording).getSeconds();
        double expectedNumber = Math.ceil(secondsBetween / 60.0); // ceiling to get last minute

        assertEquals("Unexpected number of subintervals", expectedNumber, metrics.getData().size(), 0.001);
    }

    @Test
    public void shouldHaveConsecutiveValueOnSubintervals() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();

        LocalDateTime prevDate = LocalDateTime.from(formatter.parse(metrics.getData().get(0).getLabel()));
        for (int i = 1; i < metrics.getData().size(); i++) {
            LocalDateTime date = LocalDateTime.from(formatter.parse(metrics.getData().get(i).getLabel()));
            assertTrue("Unexpected label value", date.isAfter(prevDate));
            prevDate = date;
        }
    }

    @Test
    public void shouldHaveCorrectLabelOnFirstSubinterval() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();
        LocalDateTime firstSubinterval = LocalDateTime.from(formatter.parse(metrics.getData().get(0).getLabel()));

        assertEquals("Unexpected label value of first subinterval",
            beforeRecoding.truncatedTo(ChronoUnit.MINUTES), firstSubinterval);
    }

    @Test
    public void shouldHaveCorrectLabelOnLastSubinterval() throws Exception {
        HistogramBean<HistogramDataPoint> metrics = getMetrics();
        int last = metrics.getData().size() - 1;
        LocalDateTime lastSubinterval = LocalDateTime.from(formatter.parse(metrics.getData().get(last).getLabel()));

        assertEquals("Unexpected label value of last subinterval",
            tenMinutesAfterRecording.truncatedTo(ChronoUnit.MINUTES), lastSubinterval);
    }
}
