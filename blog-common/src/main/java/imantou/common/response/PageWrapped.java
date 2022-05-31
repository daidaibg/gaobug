package imantou.common.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类
 */
@Data
public class PageWrapped implements Serializable {
    /**
     * 总记录数
     */
    private Integer total;
    /**
     * 每页记录数
     */
    private Integer size;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前页数
     */
    private Integer current;
    /**
     * 列表数据
     */
    private List<?> records;

    /**
     * 分页
     *
     * @param list    列表数据
     * @param total   总记录数
     * @param size    每页记录数
     * @param current 当前页数
     */
    public PageWrapped(List<?> list, int total, int size, int current) {
        this.records = list;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    /**
     * 分页
     */
    public PageWrapped(IPage<?> page) {
        this.records = page.getRecords();
        this.total = (int) page.getTotal();
        this.size = (int) page.getSize();
        this.current = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

}