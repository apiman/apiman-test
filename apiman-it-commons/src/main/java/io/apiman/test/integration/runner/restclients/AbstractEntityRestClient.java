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

package io.apiman.test.integration.runner.restclients;

import static io.apiman.test.integration.runner.RestAssuredUtils.givenManager;
import static io.apiman.test.integration.runner.RestAssuredUtils.withManager;

import io.apiman.manager.api.beans.metrics.HistogramIntervalType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;

/**
 * @author jcechace
 */
public class AbstractEntityRestClient<Entity, NewEntity> extends AbstractRestWrapper
        implements EntityRestClient<Entity, NewEntity> {

    private final String resourcePath;
    private final Class<Entity> clazz;
    private Entity bean;
    private final DateTimeFormatter formatter;

    public AbstractEntityRestClient(String resourcePath, Class<Entity> clazz) {
        this.resourcePath = resourcePath;
        this.clazz = clazz;

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @Override
    public EntityRestClient<Entity, NewEntity> fetch(String idOrVersion) {
        this.bean = withManager().get(getResourcePath(idOrVersion)) .as(clazz);
        return this;
    }

    @Override
    public EntityRestClient<Entity, NewEntity> create(NewEntity newBean) {
        this.bean =  withManager().content(newBean).post(resourcePath).as(clazz);;
        return this;
    }

    @Override
    public void peek(String idOrVersion) {
        ResponseSpecification spec = new ResponseSpecBuilder().expectStatusCode(200).build();
        peek(idOrVersion, spec);
    }

    @Override
    public void peek(String idOrVersion, ResponseSpecification spec) {
        givenManager().
            get(getResourcePath(idOrVersion)).
        then().
            specification(spec);
    }

    @Override
    public Entity getBean() {
        return bean;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getResourcePath(String idOrVersion) {
        return resourcePath + "/" + idOrVersion;
    }

    protected void setBean(Entity bean) {
        this.bean = bean;
    }

    protected <Bean> Bean getMetrics(LocalDateTime from, LocalDateTime to, String idOrVersion, String metricsPath, Class<Bean> clazz) {
        return  withManager().given().
                    param("from", formatter.format(from)).
                    param("to", formatter.format(to)).
                then().
                    get(getResourcePath(idOrVersion) + metricsPath).
                    as(clazz);
    }

    protected <Bean> Bean getMetrics(LocalDateTime from, LocalDateTime to, String idOrVersion, String metricsPath, Class<Bean> clazz,
            HistogramIntervalType interval) {
        return  givenManager().
                    param("from", formatter.format(from)).
                    param("to", formatter.format(to)).
                    param("interval", interval).
                when().
                    get(getResourcePath(idOrVersion) + metricsPath).
                    as(clazz);
    }
}
