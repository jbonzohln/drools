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
package org.kie.drl.engine.runtime.kiesession.local.service;

import java.util.Optional;

import org.kie.api.runtime.KieSession;
import org.kie.drl.engine.runtime.kiesession.local.model.EfestoInputDrlKieSessionLocal;
import org.kie.drl.engine.runtime.kiesession.local.model.EfestoOutputDrlKieSessionLocal;
import org.kie.drl.engine.runtime.kiesession.local.utils.DrlRuntimeHelper;
import org.kie.efesto.runtimemanager.api.model.EfestoInput;
import org.kie.efesto.runtimemanager.api.model.EfestoRuntimeContext;
import org.kie.efesto.runtimemanager.api.service.KieRuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KieRuntimeServiceDrlKieSessionLocal implements KieRuntimeService<String, KieSession, EfestoInputDrlKieSessionLocal, EfestoOutputDrlKieSessionLocal, EfestoRuntimeContext> {

    private static final Logger logger = LoggerFactory.getLogger(KieRuntimeServiceDrlKieSessionLocal.class.getName());

    @Override
    public boolean canManageInput(EfestoInput toEvaluate, EfestoRuntimeContext context) {
        return DrlRuntimeHelper.canManage(toEvaluate, context);
    }

    @Override
    public Optional<EfestoOutputDrlKieSessionLocal> evaluateInput(EfestoInputDrlKieSessionLocal toEvaluate, EfestoRuntimeContext context) {
        return canManageInput(toEvaluate, context) ? DrlRuntimeHelper.execute(toEvaluate, context) : Optional.empty();
    }

    @Override
    public String getModelType() {
        return "drl";
    }
}
