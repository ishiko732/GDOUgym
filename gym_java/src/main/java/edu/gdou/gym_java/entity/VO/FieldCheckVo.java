package edu.gdou.gym_java.entity.VO;

import edu.gdou.gym_java.entity.model.TimeArrange;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FieldCheckVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String time;

    private Double money;

    private String status;

    private String name;

    private String card;

    private String date;

    private String userName;

    private Boolean flag;

    private List<TimeArrange> timeArrangeList;
}
