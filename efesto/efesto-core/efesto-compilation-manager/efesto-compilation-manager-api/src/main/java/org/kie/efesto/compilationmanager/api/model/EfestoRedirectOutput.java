/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.efesto.compilationmanager.api.model;

import org.kie.efesto.common.api.model.FRI;

import java.util.List;

/**
 * A  <code>CompilationOutput</code> from one engine that will
 * be an <code>EfestoResource</code> for another one.
 * This will be translated to a <code>GeneratedRedirectResource</code>,
 * that has a specif json-format and semantic.
 */
public abstract class EfestoRedirectOutput<T> extends AbstractEfestoCallableCompilationOutput implements EfestoResource<T> {

    private final String targetEngine;

    /**
     * This is the <b>payload</b> to forward to the target compilation-engine
     */
    private final T content;

    protected EfestoRedirectOutput(FRI fri, String targetEngine, T content) {
        super(fri, (List<String>) null);
        this.targetEngine = targetEngine;
        this.content = content;
    }

    public String getTargetEngine() {
        return targetEngine;
    }

    @Override
    public T getContent() {
        return content;
    }

}
