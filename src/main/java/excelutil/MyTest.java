package excelutil;

import com.alibaba.fastjson.JSONObject;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author zhaojiatao
 * @date 2019-07-23
 */
public class MyTest {


    @Data
    static class Gwyobject{
        private Integer item01;
        private String item02;
        private String item03;
        private String item04;
        private String item05;
        private String item06;
        private String item07;
        private String item08;
        private String item09;
    }


    /**
     * 读取
     */
    @Test
    public void myTest06() throws IOException, BiffException, WriteException {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource resource = resolver.getResource("classpath:data.json");

        List<Gwyobject> gwyobjects = JSONObject.parseArray(FileUtils.readFileToString(resource.getFile(), "UTF-8"), Gwyobject.class);




        File targetFile = new File("/Users/zhaojiatao/IdeaProjects/javase/src/main/resources/2019temp2.xls");
        // 创建一个工作簿
        WritableWorkbook targetWorkbook = Workbook.createWorkbook(targetFile);
        // 创建一个工作表
        WritableSheet targetSheet = targetWorkbook.createSheet("汇总", 0);


        File xlsFile = new File("/Users/zhaojiatao/IdeaProjects/javase/src/main/resources/2019temp.xls");

        // 获得工作簿对象
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        // 获得所有工作表
        Sheet[] sheets = workbook.getSheets();
        // 遍历工作表
        if (sheets != null)
        {
            for (Sheet sheet : sheets)
            {
                // 获得行数
                int rows = sheet.getRows();
                // 获得列数
                int cols = sheet.getColumns();
                // 读取数据
                for (int row = 0; row < rows; row++)
                {
                    //部门名称
                    String item03="";
                    //部门代码
                    String item05="";
                    //职位代码
                    String item06="";



                    for (int col = 0; col < cols; col++)
                    {

                        Cell cell = sheet.getCell(col, row);

                        targetSheet.addCell(new Label(col,row,cell.getContents()));

                        if(col==2){
                            item03=cell.getContents();
                        }
                        if(col==3){
                            item05=cell.getContents();
                        }
                        if(col==6){
                            item06=cell.getContents();
                        }
                    }

                    if(row == 0){

                        targetSheet.addCell(new Label(cols,row,"2019笔试成绩"));
                        targetSheet.addCell(new Label(cols+1,row,"2019面试成绩"));
                        targetSheet.addCell(new Label(cols+2,row,"2019总成绩"));
                        targetSheet.addCell(new Label(cols+3,row,"2018笔试成绩"));
                        targetSheet.addCell(new Label(cols+4,row,"2018面试成绩"));
                        targetSheet.addCell(new Label(cols+5,row,"2018总成绩"));
                        targetSheet.addCell(new Label(cols+6,row,"2017笔试成绩"));
                        targetSheet.addCell(new Label(cols+7,row,"2017面试成绩"));
                        targetSheet.addCell(new Label(cols+8,row,"2017总成绩"));

                    }

                    if(row != 0){

                        //部门名称
                        final String item03Final=item03;
                        //部门代码
                        final String item05Final=item05;
                        //职位代码
                        final String item06Final=item06;


                        Gwyobject gwyobject2019 = gwyobjects.stream().filter(g ->
                                g.item03.trim().equalsIgnoreCase(item03Final) &&
                                        g.item05.trim().equalsIgnoreCase(item05Final) &&
                                        g.item06.trim().equalsIgnoreCase(item06Final) &&
                                        g.item01==2019
                        ).findFirst().orElse(null);

                        if(Objects.nonNull(gwyobject2019)){
                            targetSheet.addCell(new Label(cols,row,gwyobject2019.item07));
                            targetSheet.addCell(new Label(cols+1,row,gwyobject2019.item08));
                            targetSheet.addCell(new Label(cols+2,row,gwyobject2019.item09));
                        }


                        Gwyobject gwyobject2018 = gwyobjects.stream().filter(g ->
                                g.item03.trim().equalsIgnoreCase(item03Final) &&
                                        g.item05.trim().equalsIgnoreCase(item05Final) &&
                                        g.item06.trim().equalsIgnoreCase(item06Final) &&
                                        g.item01==2018
                        ).findFirst().orElse(null);

                        if(Objects.nonNull(gwyobject2018)){
                            targetSheet.addCell(new Label(cols+3,row,gwyobject2018.item07));
                            targetSheet.addCell(new Label(cols+4,row,gwyobject2018.item08));
                            targetSheet.addCell(new Label(cols+5,row,gwyobject2018.item09));
                        }

                        Gwyobject gwyobject2017 = gwyobjects.stream().filter(g ->
                                g.item03.trim().equalsIgnoreCase(item03Final) &&
                                        g.item05.trim().equalsIgnoreCase(item05Final) &&
                                        g.item06.trim().equalsIgnoreCase(item06Final) &&
                                        g.item01==2017
                        ).findFirst().orElse(null);

                        if(Objects.nonNull(gwyobject2017)){
                            targetSheet.addCell(new Label(cols+6,row,gwyobject2017.item07));
                            targetSheet.addCell(new Label(cols+7,row,gwyobject2017.item08));
                            targetSheet.addCell(new Label(cols+8,row,gwyobject2017.item09));
                        }





                    }


                }
            }
        }
        workbook.close();


        targetWorkbook.write();
        targetWorkbook.close();


    }








}

