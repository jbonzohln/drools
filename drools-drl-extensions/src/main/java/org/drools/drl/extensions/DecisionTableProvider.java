/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.drools.drl.extensions;

import java.util.List;

import org.kie.api.internal.utils.KieService;
import org.kie.api.io.Resource;
import org.kie.internal.builder.DecisionTableConfiguration;

public interface DecisionTableProvider extends KieService {

    String loadFromResource(Resource resource,
                            DecisionTableConfiguration configuration);

    List<String> loadFromInputStreamWithTemplates(Resource resource,
                                                  DecisionTableConfiguration configuration);


}
