/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.executor;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.Operation;

import java.io.IOException;

/**
 * @author mdogan 6/7/13
 */
public final class CancellationOperation extends Operation {

    private String uuid;
    private boolean interrupt;
    private transient boolean response;

    public CancellationOperation() {
    }

    public CancellationOperation(String uuid, boolean interrupt) {
        this.uuid = uuid;
        this.interrupt = interrupt;
    }

    public void beforeRun() throws Exception {
    }

    public void run() throws Exception {
        DistributedExecutorService service = getService();
        response = service.cancel(uuid, interrupt);
    }

    public void afterRun() throws Exception {
    }

    public boolean returnsResponse() {
        return true;
    }

    public Object getResponse() {
        return response;
    }

    protected void writeInternal(ObjectDataOutput out) throws IOException {
        out.writeUTF(uuid);
        out.writeBoolean(interrupt);
    }

    protected void readInternal(ObjectDataInput in) throws IOException {
        uuid = in.readUTF();
        interrupt = in.readBoolean();
    }
}
