USE [ForJira]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

drop TABLE [dbo].[Jira_view_Employee]
CREATE TABLE [dbo].[Jira_view_Employee](
	[dbo].[Jira_view_Employee] [bigint] IDENTITY(1,1) NOT NULL,
	[Description] [nvarchar](1024) NULL,
	[Name] [nvarchar](512) NULL,
	[Deleted] [int] NULL DEFAULT 0,
	[In_state] [int] NULL DEFAULT 1,
	[LoginAD] [nvarchar](10) NULL,
	[Login_Manager] [nvarchar](10) NULL ,
	[Date_created] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE INDEX I_Jira_view_Employee_AK1 
    ON [dbo].[Jira_view_Employee] (LoginAD);
GO    

CREATE INDEX I_Jira_view_Employee_AK2 
    ON [dbo].[Jira_view_Employee] (Login_Manager);
GO  

CREATE INDEX I_Jira_view_Employee_AK3 
    ON [dbo].[Jira_view_Employee] (Name);
GO  

CREATE TABLE [dbo].[Jira_tab_Resources](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Structural_code] [nvarchar](1024) NULL,
	[DaysToComplete] [real] NULL,
	[Description] [nvarchar](1024) NULL,
	[Login_Expert] [nvarchar](10) NULL,
	[Login_Owner] [nvarchar](10) NULL,
	[Login_Specialist] [nvarchar](10) NULL,
	[Name] [nvarchar](512) NULL,
	[Date_created] [datetime] NULL,
	[ResourceType] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE INDEX I_Jira_tab_Resources_AK1 
    ON [dbo].[Jira_tab_Resources] (Structural_code);
GO    

CREATE INDEX I_Jira_tab_Resources_AK2 
    ON [dbo].[Jira_tab_Resources] (Login_Expert);
GO  

CREATE INDEX I_Jira_tab_Resources_AK3 
    ON [dbo].[Jira_tab_Resources] (Login_Owner);
GO  

CREATE INDEX I_Jira_tab_Resources_AK4 
    ON [dbo].[Jira_tab_Resources] (Login_Specialist);
GO 

drop TABLE [dbo].[Jira_tab_Resources_Issue]
CREATE TABLE [dbo].[Jira_tab_Resources_Issue](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Structural_code] [nvarchar](1024) NULL,
	[Сomment] [ntext] NULL,
	[ID_Issue] [nvarchar](100) NULL,
	[ID_Subtask] [nvarchar](100) NULL,
	[Login_Expert] [nvarchar](10) NULL,
	[Login_Owner] [nvarchar](10) NULL,
	[Login_Specialist] [nvarchar](10) NULL,
	[Name] [nvarchar](512) NULL,
	[Date_created] [datetime] NULL,
	[ID_Status] [bigint] NULL,
	[ID_Employee] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE INDEX I_Jira_tab_Resources_Issue_AK1 
    ON [dbo].[Jira_tab_Resources_Issue] (Structural_code);
GO    

CREATE INDEX I_Jira_tab_Resources_Issue_AK2 
    ON [dbo].[Jira_tab_Resources_Issue] (ID_Issue);
GO  

CREATE INDEX I_Jira_tab_Resources_Issue_AK3 
    ON [dbo].[Jira_tab_Resources_Issue] (ID_Subtask);
GO  

CREATE INDEX I_Jira_tab_Resources_Issue_AK4 
    ON [dbo].[Jira_tab_Resources_Issue] (Login_Specialist);
GO 
CREATE INDEX I_Jira_tab_Resources_Issue_AK5 
    ON [dbo].[Jira_tab_Resources_Issue] (Login_Expert);
GO 
CREATE INDEX I_Jira_tab_Resources_Issue_AK6 
    ON [dbo].[Jira_tab_Resources_Issue] (Login_Owner);
GO 
CREATE INDEX I_Jira_tab_Resources_Issue_AK7 
    ON [dbo].[Jira_tab_Resources_Issue] (ID_Employee);
GO 

drop table [dbo].[Jira_tab_StatusAccess]
CREATE TABLE [dbo].[Jira_tab_StatusAccess](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Description] [nvarchar](1024) NULL,
	[Name] [nvarchar](512) NULL,
	[Deleted] [int] NULL DEFAULT 0,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

INSERT INTO  [dbo].[Jira_tab_StatusAccess] (Name) VALUES ('Новый');
INSERT INTO  [dbo].[Jira_tab_StatusAccess] (Name) VALUES ('Выдан');
INSERT INTO  [dbo].[Jira_tab_StatusAccess] (Name) VALUES ('Продление');
INSERT INTO  [dbo].[Jira_tab_StatusAccess] (Name) VALUES ('Архив');



drop table [dbo].Jira_tab_AccessTemplate
CREATE TABLE [dbo].Jira_tab_AccessTemplate(
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	Date_created [datetime] NULL,
	[Name] [nvarchar](512) NULL,
	[Description] [nvarchar](1024) NULL,	
	[Deleted] [int] NULL DEFAULT 0,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO




INSERT INTO  [dbo].Jira_tab_AccessTemplate (Name, Date_created) VALUES ('Новый', getdate());


drop table [dbo].Jira_tab_TemplateComponent
CREATE TABLE [dbo].Jira_tab_TemplateComponent(
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ID_template] [bigint] NOT NULL,
	[ID_Resource] [bigint] NOT NULL,
	[Deleted] [int] NULL DEFAULT 0,
	[Description] [nvarchar](512) NULL,
	[FreeText] [ntext] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE INDEX I_Jira_tab_TemplateComponent_AK1 
    ON [dbo].Jira_tab_TemplateComponent ([ID_template], [ID_Resource]);
GO    




CREATE TABLE [dbo].[Jira_tab_ApprovalChain](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Structural_code] [nvarchar](1024) NULL,
	[ID_Issue] [nvarchar](100) NULL,
	[ID_Subtask] [nvarchar](100) NULL,
	[Login_Expert] [nvarchar](10) NULL,
	[Login_Owner] [nvarchar](10) NULL,
	[Date_created] [datetime] NULL,
	[ID_Employee] [bigint] NULL,
	[Resolution] [int] NULL,
	[Resource_ID] [bigint] NOT NULL,
	[ApprovalLevel] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] 

GO

CREATE INDEX I_Jira_tab_ApprovalChain_AK1 
    ON [dbo].[Jira_tab_ApprovalChain] (Structural_code);
GO    

CREATE INDEX I_Jira_tab_ApprovalChain_AK2 
    ON [dbo].[Jira_tab_ApprovalChain] (ID_Issue);
GO  

CREATE INDEX I_Jira_tab_ApprovalChain_AK3 
    ON [dbo].[Jira_tab_ApprovalChain] (ID_Subtask);
GO  

CREATE INDEX I_Jira_tab_ApprovalChain_AK4 
    ON [dbo].[Jira_tab_ApprovalChain] (Login_Specialist);
GO 
CREATE INDEX I_Jira_tab_ApprovalChain_AK5 
    ON [dbo].[Jira_tab_ApprovalChain] (Login_Expert);
GO 
CREATE INDEX I_Jira_tab_ApprovalChain_AK6 
    ON [dbo].[Jira_tab_ApprovalChain] (Login_Owner);
GO 
CREATE INDEX I_Jira_tab_ApprovalChain_AK7
    ON [dbo].[Jira_tab_ApprovalChain] (ID_Employee);
GO



CREATE TABLE [dbo].[Jira_tab_AccessHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Structural_code] [nvarchar](1024) NULL,
	[Name] [nvarchar](512) NULL,
	[Date_created] [datetime] NULL,
	[Date_end] [datetime] NULL,
	[ID_Employee] [bigint] NULL,
	[Status_ID] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] 

GO

CREATE INDEX I_Jira_tab_AccessHistory_AK1
    ON [dbo].[Jira_tab_AccessHistory] (ID_Employee);
GO

alter table dbo.Jira_tab_ApprovalChain add column Login_Specialist nvarchar(10)
GO


