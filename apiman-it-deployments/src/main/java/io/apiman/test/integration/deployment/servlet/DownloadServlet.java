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

package io.apiman.test.integration.deployment.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple servlet which returns as much zero bytes in body of response as you define in http header {@link #HTTP_HEADER}
 * or in get/post parameter {@link #PARAMETER}
 *
 * @author jkaspar
 */
public class DownloadServlet extends HttpServlet {

    public static final String HTTP_HEADER = "X-download-n-bytes";
    public static final String PARAMETER = "download-n-bytes";

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        setBytesIntoBody(req, resp);
    }

    private void setBytesIntoBody(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int bytes = 0;

        if (req.getHeader(HTTP_HEADER) != null) {
            bytes = Integer.valueOf(req.getHeader(HTTP_HEADER));
        } else if (req.getParameter(PARAMETER) != null) {
            bytes = Integer.valueOf(req.getParameter(PARAMETER));
        }

        resp.setContentType("application/octet-stream");
        resp.getOutputStream().write(new byte[bytes]);
    }
}
