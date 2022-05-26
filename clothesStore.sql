USE [master]
GO
/****** Object:  Database [PetStore]    Script Date: 6/3/2019 10:55:30 PM ******/
CREATE DATABASE [ClothesStore]
GO
USE [ClothesStore]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 6/3/2019 10:55:31 PM ******/
SET ANSI_NULLS ON 
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[address] [varchar](50) ,
	[phone] [varchar](12) NOT NULL,
	[email] [varchar](30) NOT NULL,
	[userName] [varchar](100) NOT NULL unique,
	--password: default = 1
	[password] [char](64) NOT NULL default('6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B'),
	[enabled] [bit] NOT NULL default(1),
	[role] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customer](
	[id] [int] NOT NULL,
	[category] varchar(50) NOT NULL CHECK([category] IN ('Gold','Silver','Copper')),
	[shipToAddress] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Employee](
	[id] [int] NOT NULL,
	[salary] [money] NOT NULL,
	[department_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderHeader](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime] NOT NULL,
	[status] [varchar](30) NULL,
	[customer_id] [int] NOT NULL,
	[employee_id] [int] ,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[product_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [float] NOT NULL,
	[discount] [float] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[price] [float] NOT NULL,
	[discount] [float] NOT NULL,
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Category] ON 

INSERT [dbo].[Category] ([id], [name]) VALUES (1, N'Shirts')
INSERT [dbo].[Category] ([id], [name]) VALUES (2, N'Sport wears')
INSERT [dbo].[Category] ([id], [name]) VALUES (3, N'Outwears')
SET IDENTITY_INSERT [dbo].[Category] OFF
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (1, N'Year Of The Tiger Tee', 204.99, 0.1, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (2, N'Lord Angel Pocket Tee', 984.800, 0.05, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (3, N'Lord Devil Pocket Tee', 74.11, 0.05, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (4, N'Limbo Embroidered Tee', 1036, 0, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (5, N'Lucky Nerm Tee', 829.300, 0.15, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (6, N'Pouty Face Tee', 140.55, 0.1, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (7, N'F.U.N Tee', 829.300 , 0.1, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (8, N'Wheres My Hug Tee', 829.300, 0.1, 1)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (9, N'Dance Party Denim Work Jacket', 2850.600 , 0, 3)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (10, N'Heaven And Hell Denim Jacket', 2850.600, 0.1, 3)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (11, N'Highway To Heck Work Jacket', 2850.600, 0.05, 3)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (12, N'Holy Cargo Jacket', 2850.600, 0.15, 3)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (13, N'Limbo Shirt Jacket', 2850.600, 0.05, 3)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (14, N'Friday Jr Hoodie', 2591.500 , 0.05, 2)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (15, N'Promised Land Hoodie', 2591.500, 0.1, 2)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (16, N'Devil Baby Hoodie(Dark Sage)', 2591.500, 0.05, 2)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (17, N'Devil Baby Hoodie (Black)', 2591.500, 0.1, 2)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (18, N'Sensai Embroidered Hoodie', 2591.500, 0, 2)
INSERT [dbo].[Product] ([id], [description], [price], [discount], [category_id]) VALUES (19, N'Sensai Embroidered Hoodie(Black)', 2591.500, 0.15, 2)
SET IDENTITY_INSERT [dbo].[Product] OFF
SET IDENTITY_INSERT [dbo].[Account] ON 
INSERT [dbo].[Account] ([id], [name], [address], [phone], [email], [userName],[role]) VALUES
(1, N'Admin', N'9652 Los Angeles', N'0123456789', N'a@clothestore.com','admin', 'ROLE_ADMIN'),
(2, N'Employee1', N'5747 Shirley Drive', N'1234567890', N'e1@clothestore.com', 'e1', 'ROLE_EMPLOYEE'),
(3, N'Employee2', N'3841 Silver Oaks Place', N'2345678901', N'e2@clothestore.com', 'e2', 'ROLE_EMPLOYEE'),
(4, N'Customer1', N'1873 Lion Circle', N'5678901234', N'c1@gmail.com', 'c1','ROLE_CUSTOMER'),
(5, N'Customer2', N'5747 Shirley Drive', N'6789872314', N'c2@gmail.com', 'c2', 'ROLE_CUSTOMER')
SET IDENTITY_INSERT [dbo].[Account] OFF

INSERT [dbo].[Customer] ([id], [category], [shipToAddress]) VALUES (4, 'Copper', N'1873 Lion Circle')
INSERT [dbo].[Customer] ([id], [category], [shipToAddress]) VALUES (5, 'Copper', N'5747 Shirley Drive')

INSERT [dbo].[Employee] ([id], [salary], [department_id]) VALUES 
(1, 1200, 1),(2, 1000, 2),(3, 800, 2)

SET ANSI_PADDING ON
GO
ALTER TABLE [dbo].[Account] ADD UNIQUE NONCLUSTERED 
(
	[userName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Product] ADD  DEFAULT ((0)) FOR [discount]
GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [dbo].[Account] ([id])
GO
ALTER TABLE [dbo].[OrderHeader]  WITH CHECK ADD FOREIGN KEY([customer_id])
REFERENCES [dbo].[Customer] ([id])
GO
ALTER TABLE [dbo].[OrderHeader]  WITH CHECK ADD FOREIGN KEY([employee_id])
REFERENCES [dbo].[Employee] ([id])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [dbo].[OrderHeader] ([id])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [dbo].[Product] ([id])
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [dbo].[Category] ([id])
GO
ALTER TABLE [dbo].[OrderHeader]  WITH CHECK ADD CHECK  (([status]='canceled' OR [status]='paid' OR [status]='delivered' OR [status]='packaged' OR [status]='confirmed' OR [status]='new'))
GO
USE [master]
GO
ALTER DATABASE [ClothesStore] SET  READ_WRITE 
GO


