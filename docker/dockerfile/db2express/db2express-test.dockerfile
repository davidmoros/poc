FROM davidmoros/db2express-c

ENV DB2INST1_PASSWORD=db2inst1
ENV LICENSE=accept

ENV DIR_TMP=/tmp/db2-atlas/

COPY scripts/* $DIR_TMP

RUN su - db2inst1 -c "db2start && \
    cd $DIR_TMP && \
    $DIR_TMP/ddl.sh && \
    $DIR_TMP/dml.sh"
