/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.undertow.servlet.test.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import io.undertow.httpcore.OutputChannel;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.RangeAwareResource;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.util.ETag;
import io.undertow.util.MimeMappings;

/**
 * @author Stuart Douglas
 */
public class TestResourceLoader extends ClassPathResourceManager {

    public TestResourceLoader(final Class<?> testClass) {
        super(testClass.getClassLoader(), testClass.getPackage().getName().replace(".", "/"));
    }

    @Override
    public Resource getResource(String path) throws IOException {
        final Resource delegate = super.getResource(path);
        if(delegate == null) {
            return delegate;
        }
        return new TestResource(delegate);
    }

    private static class TestResource implements RangeAwareResource {
        private final Resource delegate;

        TestResource(Resource delegate) {
            this.delegate = delegate;
        }

        @Override
        public String getPath() {
            return delegate.getPath();
        }

        @Override
        public Date getLastModified() {
            return delegate.getLastModified();
        }

        @Override
        public String getLastModifiedString() {
            return delegate.getLastModifiedString();
        }

        @Override
        public ETag getETag() {
            return delegate.getETag();
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public boolean isDirectory() {
            return delegate.isDirectory();
        }

        @Override
        public List<Resource> list() {
            return delegate.list();
        }

        @Override
        public String getContentType(MimeMappings mimeMappings) {
            return delegate.getContentType(mimeMappings);
        }

        @Override
        public void serveBlocking(OutputStream outputStream, HttpServerExchange exchange) throws IOException {
            delegate.serveBlocking(outputStream, exchange);
        }

        @Override
        public void serveAsync(OutputChannel stream, HttpServerExchange exchange) {
            delegate.serveAsync(stream, exchange);
        }

        @Override
        public Long getContentLength() {
            return delegate.getContentLength();
        }

        @Override
        public String getCacheKey() {
            return delegate.getCacheKey();
        }

        @Override
        public File getFile() {
            return delegate.getFile();
        }

        @Override
        public Path getFilePath() {
            return delegate.getFilePath();
        }

        @Override
        public File getResourceManagerRoot() {
            return delegate.getResourceManagerRoot();
        }

        @Override
        public Path getResourceManagerRootPath() {
            return delegate.getResourceManagerRootPath();
        }

        @Override
        public URL getUrl() {
            return delegate.getUrl();
        }

        @Override
        public void serveRangeBlocking(OutputStream outputStream, HttpServerExchange exchange, long start, long end) throws IOException {
            ((RangeAwareResource)delegate).serveRangeBlocking(outputStream, exchange, start, end);
        }

        @Override
        public void serveRangeAsync(OutputChannel outputStream, HttpServerExchange exchange, long start, long end) {
            ((RangeAwareResource)delegate).serveRangeAsync(outputStream, exchange, start, end);
        }

        @Override
        public boolean isRangeSupported() {
            return delegate instanceof RangeAwareResource && ((RangeAwareResource) delegate).isRangeSupported();
        }
    }
}
