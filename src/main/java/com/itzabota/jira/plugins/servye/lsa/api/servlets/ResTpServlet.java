package com.itzabota.jira.plugins.servye.lsa.api.servlets;

import javax.inject.Named;
import javax.servlet.http.HttpServlet;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.webresource.WebResourceManager;

//import com.atlassian.jira.template.velocity.

//@Named("resTpServlet")
public class ResTpServlet extends HttpServlet {
/*
	@ComponentImport
	private TemplateRenderer templateRenderer;	

	@ComponentImport
	private WebResourceManager webResourceManager;
		

    private static final Logger log = LoggerFactory.getLogger(BkClsServlet.class);
    
    @Inject
	public BkClsServlet(final TemplateRenderer templateRenderer,
			final WebResourceManager webResourceManager, 
			final BkUserGroup bkUserGroup)
	//public BkClsServlet(final TemplateRenderer templateRenderer, final WebResourceManager webResourceManager)
    {
        this.templateRenderer = templateRenderer;
		this.webResourceManager = webResourceManager;
		this.bkUserGroup = bkUserGroup;
    }      
    
//    protected BkClsServlet() {
//    	
//    }
    
    private void initGet() {
//    	tstFillThread.fill();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {    	
    	initGet();    	
		resp.setContentType("text/html;charset=utf-8");
		ConfluenceUser confluenceUser = AuthenticatedUserThreadLocal.get();
		if (confluenceUser == null || !bkUserGroup.isAllowed(confluenceUser)) {
			resp.getWriter().write("<html><body>Справочники бизнес-компетенций недоступны. Необходимо войти в приложение с определенными ролями.</body></html>");
			return;
		}
		
		
		this.webResourceManager.requireResource("com.it-zabota.confluence.plugins.bk:bk-resources");
		this.webResourceManager.requireResource("com.it-zabota.confluence.plugins.bk:bk-dialog");
		this.webResourceManager.requireResource("com.it-zabota.confluence.plugins.bk:bk-label");
		this.webResourceManager.requireResource("com.it-zabota.confluence.plugins.bk:bk-select2");
		templateRenderer.render("/templates/cls.vm", resp.getWriter());    			    			

    }
*/    
}
