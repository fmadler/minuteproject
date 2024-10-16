package net.sf.minuteProject.console;

import static net.sf.minuteProject.console.utils.UIUtils.addSeparator;
import static net.sf.minuteProject.console.utils.UIUtils.createTabPanel;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import net.miginfocom.swing.MigLayout;
import net.sf.minuteProject.console.panel.CommonPanel;
import net.sf.minuteProject.console.panel.ConventionPanel;
import net.sf.minuteProject.console.panel.EnrichmentEntityPanel;
import net.sf.minuteProject.console.panel.EnrichmentFieldPanel;
import net.sf.minuteProject.console.panel.FilterPanel;
import net.sf.minuteProject.console.panel.ModelAccessPanel;
import net.sf.minuteProject.console.panel.ModelCommonPanel;
import net.sf.minuteProject.console.panel.PackagePanel;
import net.sf.minuteProject.console.panel.TargetPanel;
import net.sf.minuteProject.console.panel.TechnologyConventionPanel;
import net.sf.minuteProject.console.panel.TechnologyLimitationPanel;
import net.sf.minuteProject.console.panel.WebServiceAccessPanel;
import net.sf.minuteProject.console.panel.WebServiceCommonPanel;
import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.integration.bean.BasicIntegrationConfiguration;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.databasecatalog.node.Database;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;
import net.sf.minuteProject.utils.MinuteProjectUtils;
import net.sf.minuteProject.utils.catalog.CatalogUtils;

public class ConsoleSample extends JFrame{

	private static Logger logger = Logger.getLogger(ConsoleSample.class);
	private JTabbedPane tabbedPane;//, tabbedCommon;
	private ModelAccessPanel modelAccessPanel;
	private WebServiceAccessPanel webServiceAccessPanel;
	private ModelCommonPanel modelCommonPanel;
	private TargetPanel targetPanel, webserviceTarget;
	private CommonPanel commonPanel;
	private WebServiceCommonPanel webServiceCommonPanel;
	private FilterPanel filterPanel;
	private ConventionPanel conventionPanel;
	private TechnologyLimitationPanel technologyLimitationPanel;
	private TechnologyConventionPanel technologyConventionPanel;
	private EnrichmentEntityPanel enrichmentEntityPanel;
	private EnrichmentFieldPanel enrichmentFieldPanel;
	private PackagePanel packagePanel;
	private TechnologycatalogHolder technologycatalogHolder, webserviceTechnologycatalogHolder;
	private DatabasecatalogHolder databasecatalogHolder;
	private JPanel technologyInfoTab;
	private static String catalogDir, templateRootDir;
	
	public ConsoleSample (String title, String catalogDir) throws MinuteProjectException {
		super(title);
		init(catalogDir);
	}

	private void initComponents() throws MinuteProjectException {
		
		setLookAndFeel();

		tabbedPane = new JTabbedPane();
		
		modelAccessPanel = new ModelAccessPanel(this);
		webServiceAccessPanel = new WebServiceAccessPanel(this);
		modelCommonPanel = new ModelCommonPanel(this);
		webServiceCommonPanel = new WebServiceCommonPanel(this);
		targetPanel = new TargetPanel(this);
		webserviceTarget = new TargetPanel(this);
		filterPanel = new FilterPanel();
		conventionPanel = new ConventionPanel();
		technologyLimitationPanel = new TechnologyLimitationPanel(this);
		technologyConventionPanel = new TechnologyConventionPanel(this);
		enrichmentEntityPanel = new EnrichmentEntityPanel();
		enrichmentFieldPanel = new EnrichmentFieldPanel();
		packagePanel = new PackagePanel();

		tabbedPane.addTab("Data model reverse-engineering", getDataModelReverseEngineeringMainPanel());	
		//tabbedPane.addTab("Webservice model", getWebServiceModelReverseMainPanel());	
		tabbedPane.addTab("Customisation", getDataModelReverseEngineeringCustomisationPanel());	

		tabbedPane.addTab("Technology information", getTechConventionsTab());				

		tabbedPane.addTab("General information", getInformationPanel());	
		
		getContentPane().add(tabbedPane);
		
		pack();		
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private Component getEnrichmentTab() {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Entity");
		enrichmentEntityPanel.fillPanel(panel);		
		addSeparator(panel, "Field");
		enrichmentFieldPanel.fillPanel(panel);			
		return panel;
	}
	
	private Component getDataModelReverseEngineeringCustomisationPanel() {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Filter");
		filterPanel.fillPanel(panel);
		addSeparator(panel, "Package");
		packagePanel.fillPanel (panel);
		addSeparator(panel, "Convention");
		conventionPanel.fillPanel(panel);		
		return panel;
	}
	
	private Component getTechConventionsTab() throws MinuteProjectException {
		technologyInfoTab = createTabPanel(getDefaultMigLayout());
		addSeparator(technologyInfoTab, getTargetConventionTitle());
		technologyLimitationPanel.fillPanel(technologyInfoTab);	
		addSeparator(technologyInfoTab, "Conventions");
		technologyConventionPanel.fillPanel(technologyInfoTab);			
		return technologyInfoTab;
	}	

	private String getTargetConventionTitle() {
		return "Restrictions";
	}

	private Component getInformationPanel() {
		JPanel common = createTabPanel(getDefaultMigLayout());
		addSeparator(common, "Restrictions");
		commonPanel = new CommonPanel(common);
		commonPanel.fillCommonPanel();
		return common;
	}

	private Component getDataModelReverseEngineeringMainPanel() throws MinuteProjectException {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Model Access");
		modelAccessPanel.fillPanel(panel);
		addSeparator(panel, "Common configuration");
		modelCommonPanel.fillPanel (panel);
		addSeparator(panel, "Target technology");
		targetPanel.fillPanel(panel);
		return panel;
	}

	private Component getWebServiceModelReverseMainPanel() throws MinuteProjectException {
		JPanel panel = createTabPanel(getDefaultMigLayout());
		addSeparator(panel, "Webservice Access");
		webServiceAccessPanel.fillPanel(panel);
		addSeparator(panel, "Common configuration");
//		webServiceCommonPanel.fillPanel (panel);
//		addSeparator(panel, "Target technology");
		webserviceTarget.fillPanel(panel);
		return panel;
	}
	private MigLayout getDefaultMigLayout() {
		return new MigLayout("ins 20", "[para]0[][100lp, fill][80lp][125lp, fill]", "");
	}
	
	
	public void init(String catalogDir) throws MinuteProjectException {
		initCatalogs(catalogDir);
		initComponents();
	}
	
	private void initCatalogs(String catalogDir) throws MinuteProjectException {
		databasecatalogHolder = CatalogUtils.getPublishedDatabaseCatalogHolder(catalogDir);
		technologycatalogHolder = CatalogUtils.getPublishedTechnologyCatalogHolder(catalogDir);
	}

	public static void main(String args[]) {
		if (args.length>0)
			templateRootDir=args[0];
		if (args.length>1)
			catalogDir=args[1];		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ConsoleSample("MinuteProject console "+ MinuteProjectUtils.getVersion()+" - beta -", catalogDir).setVisible(true);
				} catch (MinuteProjectException mpe) {
					logger.info("error generating : "+mpe.getError());
				}
			}
		});
	}

	public void fill(BasicIntegrationConfiguration bic) throws MinuteProjectException {
		bic.setTemplateRootDir(templateRootDir);
		modelAccessPanel.fill(bic);
		modelCommonPanel.fill(bic);
		targetPanel.fill(bic);
		filterPanel.fill(bic);
		packagePanel.fill(bic);
		conventionPanel.fill(bic);
		technologyConventionPanel.fill(bic);
		bic.setCatalogDir(catalogDir);
	}

	public TechnologycatalogHolder getTechnologycatalogHolder() {
		return technologycatalogHolder;
	}

	public void setTechnologycatalogHolder(
			TechnologycatalogHolder technologycatalogHolder) {
		this.technologycatalogHolder = technologycatalogHolder;
	}

	public DatabasecatalogHolder getDatabasecatalogHolder() {
		return databasecatalogHolder;
	}

	public void setDatabasecatalogHolder(DatabasecatalogHolder databasecatalogHolder) {
		this.databasecatalogHolder = databasecatalogHolder;
	}

	public ModelAccessPanel getModelAccessPanel() {
		return modelAccessPanel;
	}

	public ModelCommonPanel getModelCommonPanel() {
		return modelCommonPanel;
	}

	public TargetPanel getTargetPanel() {
		return targetPanel;
	}

	public void applyCurrentPrimaryKeyPolicy(Database database) {
		modelCommonPanel.applyCurrentPrimaryKeyPolicy(database);
	}

	public static String getCatalogDir() {
		return catalogDir;
	}

	public static void setCatalogDir(String catalogDir) {
		ConsoleSample.catalogDir = catalogDir;
	}

	public TechnologyLimitationPanel getTechnologyLimitationPanel() {
		return technologyLimitationPanel;
	}

	public TechnologyConventionPanel getTechnologyConventionPanel() {
		return technologyConventionPanel;
	}

	public JPanel getTechnologyInfoTab() {
		return technologyInfoTab;
	}

	public void rebuildPanel(JPanel panel) throws MinuteProjectException {
		technologyInfoTab.removeAll();
		getTechConventionsTab ();
		technologyInfoTab.repaint();
	}
	
	
	
}