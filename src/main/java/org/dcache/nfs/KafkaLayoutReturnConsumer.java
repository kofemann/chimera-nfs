/*
 * Copyright (c) 2018 Deutsches Elektronen-Synchroton,
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Library General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this program (see the file COPYING.LIB for more
 * details); if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.dcache.nfs;

import java.util.function.Consumer;
import org.dcache.nfs.v4.ff.ff_ioerr4;
import org.dcache.nfs.v4.ff.ff_iostats4;
import org.dcache.nfs.v4.ff.ff_layoutreturn4;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * A {@link Consumer} that sends layout information to Apache Kafka.
 */
public class KafkaLayoutReturnConsumer implements Consumer<ff_layoutreturn4>{


    private KafkaTemplate<Object, ff_iostats4> iostatKafkaTemplate;
    private KafkaTemplate<Object, ff_ioerr4> ioerrKafkaTemplate;


    public void setIoStatKafkaTemplate(KafkaTemplate<Object, ff_iostats4> template) {
        iostatKafkaTemplate = template;
    }

    public void setIoErrKafkaTemplate(KafkaTemplate<Object, ff_ioerr4> template) {
        ioerrKafkaTemplate = template;
    }

    @Override
    public void accept(ff_layoutreturn4 lr ) {

        for (ff_iostats4 iostat : lr.fflr_iostats_report) {
            iostatKafkaTemplate.sendDefault(iostat);
        }

        for (ff_ioerr4 ioerr : lr.fflr_ioerr_report) {
            ioerrKafkaTemplate.sendDefault(ioerr);
        }
    }

}