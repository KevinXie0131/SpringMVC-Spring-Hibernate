package rml.util;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rml.model.vo.RoleChart;

public class ChartUtils {
	
	public static String getUserBarChart(List<RoleChart> datalist) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i=0;datalist!=null && i<datalist.size();i++){
			RoleChart roleChart = datalist.get(i);
			dataset.addValue(roleChart.getCount(), "Role", roleChart.getName());
		}
	
		JFreeChart chart = ChartFactory.createBarChart3D("User Type Statistics", //title
				                      "Role name",//X axis
				                      "Number",//Y axis
				                      dataset, 
				                      PlotOrientation.VERTICAL, //
				                      true,//subtitle
				                      true,//tips
				                      true); //url
		
		chart.getTitle().setFont(new Font("Sans-Serif",Font.BOLD,16));
		chart.getLegend().setItemFont(new Font("Sans-Serif",Font.BOLD,15));
	
		CategoryPlot categoryPlot = (CategoryPlot)chart.getPlot();
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D)categoryPlot.getDomainAxis();
		NumberAxis3D numberAxis3D = (NumberAxis3D)categoryPlot.getRangeAxis();
		categoryAxis3D.setTickLabelFont(new Font("Sans-Serif",Font.BOLD,15));
		categoryAxis3D.setLabelFont(new Font("Sans-Serif",Font.BOLD,15));
		numberAxis3D.setTickLabelFont(new Font("Sans-Serif",Font.BOLD,15));
		numberAxis3D.setLabelFont(new Font("Sans-Serif",Font.BOLD,15));
		
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);
	
		BarRenderer3D barRenderer3D = (BarRenderer3D)categoryPlot.getRenderer();
		barRenderer3D.setMaximumBarWidth(0.07);
		barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer3D.setBaseItemLabelsVisible(true);
		barRenderer3D.setBaseItemLabelFont(new Font("Sans-Serif",Font.BOLD,15));
		
	//	ServletContext context = ServletActionContext.getServletContext();
	//	String realPath = context.getRealPath("/chart");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
		String realPath = request.getSession().getServletContext().getRealPath("/chart"); 
		
		String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		filename = filename+".jpg";
	//	File file = new File(realPath + "\\" + filename);
		File file = new File(realPath + File.separator + filename);//for linux
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 600, 500);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

}
