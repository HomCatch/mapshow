<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
        <title>小荷管理系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" href="js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/index.css">
        <link rel="stylesheet" href="css/skins/_all-skins.css">
        <link href="css/xiaohesys.css" rel="stylesheet" type="text/css">
        <script src="js/jquery.min.js"></script>
    </head>

    <body class="hold-transition skin-blue sidebar-mini" style="overflow:hidden;">
        <div id="ajax-loader" style="cursor: progress; position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 10000; overflow: hidden;">
            <img src="img/ajax-loader.gif" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
        </div>
        <div class="wrapper">
            <!--头部信息-->
            <header class="main-header">
                <a href="#" target="_blank" class="logo">
                    <span class="logo-mini">
                        <img src="img/logo_xiao.png">
                    </span>
                    <span class="logo-lg">
                        <img src="img/logo_da.png">
                    </span>
                </a>
                <nav class="navbar navbar-static-top">
                    <a class="sidebar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                    </a>
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">

                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="img/user.jpg" class="user-image" alt="User Image">
                                    <span class="hidden-xs">${userName}</span>
                                    <span id="userId" style="display: none;">${userId}</span>
                                </a>
                                <ul class="dropdown-menu pull-right">

                                    <li>
                                        <a href="logout">
                                            <i class="ace-icon fa fa-power-off"></i>安全退出</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <!--左边导航-->
            <div class="main-sidebar">
                <div class="sidebar">
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="img/user.jpg" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p>${userName}</p>
                            <a>
                                <i class="fa fa-circle text-success"></i>在线</a>
                        </div>
                    </div>

                    <ul class="sidebar-menu" id="sidebar-menu">
                        <!--<li class="header">导航菜单</li>-->
                    </ul>
                </div>
            </div>
            <!--中间内容-->
            <div id="content-wrapper" class="content-wrapper">
                <div class="content-tabs">
                    <button class="roll-nav roll-left tabLeft">
                        <i class="fa fa-backward"></i>
                    </button>
                    <nav class="page-tabs menuTabs">
                        <div class="page-tabs-content" id="zzq_tab" style="margin-left: 0px;">
                             <!-- userId == 10000时菜单只显示滤芯更换计划菜单，使用js操作 -->
                        </div>
                    </nav>
                    <button class="roll-nav roll-right tabRight">
                        <i class="fa fa-forward" style="margin-left: 3px;"></i>
                    </button>
                    <div class="btn-group roll-nav roll-right">
                        <button class="dropdown tabClose" data-toggle="dropdown">
                            页签操作
                            <i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a class="tabReload" href="javascript:void();">刷新当前</a>
                            </li>
                            <li>
                                <a class="tabCloseCurrent" href="javascript:void();">关闭当前</a>
                            </li>
                            <li>
                                <a class="tabCloseAll" href="javascript:void();">全部关闭</a>
                            </li>
                            <li>
                                <a class="tabCloseOther" href="javascript:void();">除此之外全部关闭</a>
                            </li>
                        </ul>
                    </div>
                    <button class="roll-nav roll-right fullscreen">
                        <i class="fa fa-arrows-alt"></i>
                    </button>
                </div>
                <div class="content-iframe" style="overflow: hidden;">
                    <div class="mainContent" id="content-main" style="margin: 10px; margin-bottom: 0px; padding: 0;">
                         <!-- userId == 10000时只显示滤芯更换计划菜单，首页也修改为滤芯更换计划，使用js操作 -->
                    </div>
                </div>
            </div>
        </div>

        <script src="js/bootstrap/js/bootstrap.min.js"></script>
        <script src="js/layer/layer.js"></script>
        <script src="js/common.js"></script>
        <script src="js/index.js?time=201706230931"></script>
        <script>
            // userId == 10000时只显示滤芯更换计划菜单，首页也修改为滤芯更换计划
            var zzq_userId = $('#userId').text();
            if (zzq_userId == 10000) {
                $('#zzq_tab').html('<a href="javascript:;" class="menuTab active" data-id="filter_plan">首页-滤芯更换计划</a>')
                $('#content-main').html('<iframe class="LRADMS_iframe" width="100%" height="100%" src="filter_plan" frameborder="0" data-id="filter_plan"></iframe>')
            } else {
                $('#zzq_tab').html('<a href="javascript:;" class="menuTab active" data-id="dashboard">首页-水质仪表盘</a>')
                $('#content-main').html('<iframe class="LRADMS_iframe" width="100%" height="100%" src="dashboard" frameborder="0" data-id="dashboard"></iframe>')
            }
        </script>
    </body>

    </html>