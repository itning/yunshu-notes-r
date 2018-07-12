package top.itning.yunshunotesr.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.itning.yunshunotesr.entity.Note;
import top.itning.yunshunotesr.entity.NoteBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 对Hbase的CRUD操作
 *
 * @author itning
 */
//@Repository
public class HbaseRepository {
    private static final Logger logger = LoggerFactory.getLogger(HbaseRepository.class);

    /**
     * Hbase 表对象
     */
    private static Table table;
    /**
     * 表名
     */
    private static final String TABLE_NAME = "note";
    /**
     * 列族名
     */
    private static final String FAMILY_NAME = "shu";

    static {
        System.setProperty("hadoop.home.dir", "G:\\winutils\\hadoop-2.8.3");
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "node3:2181");
        try {
            logger.info("start build connection...");
            Connection connection = ConnectionFactory.createConnection(config);
            Admin admin = connection.getAdmin();
            //检查表存在
            if (admin.tableExists(TableName.valueOf(TABLE_NAME))) {
                logger.info("the table of name %s already exists", TABLE_NAME);
                table = connection.getTable(TableName.valueOf(TABLE_NAME.getBytes()));
            } else {
                logger.info("create table...");
                TableDescriptor descriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(TABLE_NAME))
                        .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(FAMILY_NAME.getBytes()).build())
                        .build();
                admin.createTable(descriptor);
                table = connection.getTable(TableName.valueOf(TABLE_NAME.getBytes()));
            }
        } catch (IOException e) {
            logger.error("init hbase connection error ", e);
        }
    }

    /**
     * 根据rowKey查找笔记
     *
     * @param rowKey 行键
     * @return 查找到的笔记实体
     * @throws IOException IOException
     */
    public Optional<Note> findOne(String rowKey) throws IOException {
        Result result = table.get(new Get(rowKey.getBytes()));
        if (result.size() == 0) {
            logger.debug("the rowkey %s does not exist", rowKey);
            return Optional.empty();
        }
        Note note = new Note();
        note.setId(rowKey);
        note.setGmtCreate(new Date(Bytes.toLong(result.getValue(FAMILY_NAME.getBytes(), "gmt_create".getBytes()))));
        note.setGmtModified(new Date(Bytes.toLong(result.getValue(FAMILY_NAME.getBytes(), "gmt_modified".getBytes()))));
        note.setTrash(Bytes.toBoolean(result.getValue(FAMILY_NAME.getBytes(), "trash".getBytes())));
        note.setTitle(Bytes.toString(result.getValue(FAMILY_NAME.getBytes(), "title".getBytes())));
        note.setContent(Bytes.toString(result.getValue(FAMILY_NAME.getBytes(), "content".getBytes())));
        note.setNoteBook(new NoteBook());
        return Optional.of(note);
    }

    /**
     * 根据笔记本ID,查找所有以该ID开头的笔记
     *
     * @param noteBookId 笔记本ID
     * @return 笔记集合
     * @throws IOException IOException
     */
    public Optional<List<Note>> findAll(String noteBookId) throws IOException {
        RowFilter filter = new RowFilter(CompareOperator.EQUAL, new RegexStringComparator("^" + noteBookId));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        List<Note> noteList = new ArrayList<>();
        logger.debug("the note list size is " + noteList.size());
        scanner.forEach(result -> {
            Note note = new Note();
            note.setId(Bytes.toString(result.getRow()));
            note.setGmtCreate(new Date(Bytes.toLong(result.getValue(FAMILY_NAME.getBytes(), "gmt_create".getBytes()))));
            note.setGmtModified(new Date(Bytes.toLong(result.getValue(FAMILY_NAME.getBytes(), "gmt_modified".getBytes()))));
            note.setTrash(Bytes.toBoolean(result.getValue(FAMILY_NAME.getBytes(), "trash".getBytes())));
            note.setTitle(Bytes.toString(result.getValue(FAMILY_NAME.getBytes(), "title".getBytes())));
            note.setContent(Bytes.toString(result.getValue(FAMILY_NAME.getBytes(), "content".getBytes())));
            noteList.add(note);
        });
        return Optional.of(noteList);
    }

    /**
     * 保存或覆盖笔记
     *
     * @param note 笔记实体
     * @throws IOException IOException
     */
    public void save(Note note) throws IOException {
        Put put = new Put(note.getId().getBytes());
        put.addColumn(FAMILY_NAME.getBytes(), "gmt_create".getBytes(), Bytes.toBytes(note.getGmtCreate().getTime()));
        put.addColumn(FAMILY_NAME.getBytes(), "gmt_modified".getBytes(), Bytes.toBytes(note.getGmtModified().getTime()));
        put.addColumn(FAMILY_NAME.getBytes(), "trash".getBytes(), Bytes.toBytes(note.isTrash()));
        put.addColumn(FAMILY_NAME.getBytes(), "title".getBytes(), Bytes.toBytes(note.getTitle()));
        put.addColumn(FAMILY_NAME.getBytes(), "content".getBytes(), Bytes.toBytes(note.getContent()));
        table.put(put);
    }

    /**
     * 删除笔记
     *
     * @param rowKey 行键
     * @throws IOException IOException
     */
    public void delete(String rowKey) throws IOException {
        Delete delete = new Delete(rowKey.getBytes());
        table.delete(delete);
    }
}
