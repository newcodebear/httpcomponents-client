/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.client.utils;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

public class TestURIBuilder {

    @Test
    public void testHierarchicalUri() throws Exception {
        URI uri = new URI("http", "stuff", "localhost", 80, "/some stuff", "param=stuff", "fragment");
        UriBuilder uribuilder = new UriBuilder(uri);
        URI result = uribuilder.build();
        Assert.assertEquals(uri, result);
    }

    @Test
    public void testOpaqueUri() throws Exception {
        URI uri = new URI("stuff", "some-stuff", "fragment");
        UriBuilder uribuilder = new UriBuilder(uri);
        URI result = uribuilder.build();
        Assert.assertEquals(uri, result);
    }

    @Test
    public void testOpaqueUriMutation() throws Exception {
        URI uri = new URI("stuff", "some-stuff", "fragment");
        UriBuilder uribuilder = new UriBuilder(uri).setQuery("param1&param2=stuff").setFragment(null);
        Assert.assertEquals(new URI("stuff:?param1&param2=stuff"), uribuilder.build());
    }

    @Test
    public void testHierarchicalUriMutation() throws Exception {
        UriBuilder uribuilder = new UriBuilder("/").setScheme("http").setHost("localhost").setPort(80).setPath("/stuff");
        Assert.assertEquals(new URI("http://localhost:80/stuff"), uribuilder.build());
    }

    @Test
    public void testEmpty() throws Exception {
        UriBuilder uribuilder = new UriBuilder();
        URI result = uribuilder.build();
        Assert.assertEquals(new URI(""), result);
    }

    @Test
    public void testSetUserInfo() throws Exception {
        URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff", null);
        UriBuilder uribuilder = new UriBuilder(uri).setUserInfo("user", "password");
        URI result = uribuilder.build();
        Assert.assertEquals(new URI("http://user:password@localhost:80/?param=stuff"), result);
    }

    @Test
    public void testRemoveParameters() throws Exception {
        URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff", null);
        UriBuilder uribuilder = new UriBuilder(uri).removeQuery();
        URI result = uribuilder.build();
        Assert.assertEquals(new URI("http://localhost:80/"), result);
    }

    @Test
    public void testSetParameter() throws Exception {
        URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff&blah&blah", null);
        UriBuilder uribuilder = new UriBuilder(uri).setParameter("param", "some other stuff")
            .setParameter("blah", "blah");
        URI result = uribuilder.build();
        Assert.assertEquals(new URI("http://localhost:80/?param=some+other+stuff&blah=blah"), result);
    }

    @Test
    public void testAddParameter() throws Exception {
        URI uri = new URI("http", null, "localhost", 80, "/", "param=stuff&blah&blah", null);
        UriBuilder uribuilder = new UriBuilder(uri).addParameter("param", "some other stuff")
            .addParameter("blah", "blah");
        URI result = uribuilder.build();
        Assert.assertEquals(new URI("http://localhost:80/?param=stuff&blah&blah&" +
                "param=some+other+stuff&blah=blah"), result);
    }

}