package net.sf.minuteProject.architecture.bsla.utils;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Range {
    private Long firstId;
    private Long lastId;
    public Range() {
    }

    public Range(Long firstId, Long lastId) {
        this.firstId = firstId;
        this.lastId  = lastId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public Long getFirstId() {
        return firstId;
    }

    public Long getLastId() {
        return lastId;
    }
}
