package com.dorm.entity;
import java.time.LocalDateTime;

public class RepairOrder {
    private Long id;
    private String studentId;
    private String dormNum;
    private String repairContent;
    private LocalDateTime createTime;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getDormNum() { return dormNum; }
    public void setDormNum(String dormNum) { this.dormNum = dormNum; }
    public String getRepairContent() { return repairContent; }
    public void setRepairContent(String repairContent) { this.repairContent = repairContent; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
