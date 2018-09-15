<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <nav class="navbar navbar-default ">
        <div>
            <span class="navbar-brand top-nav-logo" href="">报表系统</span>
        </div>
        <div class="nav navbar-collapse">
            <ul class="nav pull-right">
                <li class="text-center">
                    <div class="btn-group navbar-btn">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i><span id="labLoginUser">${username}</span> <b class="caret"></b></button>
                        </div>
                        <a href="" onclick="return isExit()" class="btn btn-danger" title="退出登录" data-original-title="退出登录"><i class="glyphicon glyphicon-off"></i></a>
                    </div>
                </li>
            </ul>
            <ul class="nav nav-pills navbar-form" style="font-size: inherit">

                <li id="statisticsReport" class="dropdown"><a href="">统计报表<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${statisticsReportList}">
                            <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title=report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                        </c:forEach>
                    </ul>
                </li>

                <li id="stockMReport" class="dropdown"><a href="">报表1<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${stockMReportList}">
                            <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title==report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                        </c:forEach>
                    </ul>
                </li>

                <li id="profitReport" class="dropdown"><a href="">报表2<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${profitReportList}">
                            <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title==report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                        </c:forEach>
                    </ul>
                </li>

                <li id="deviceClassReport" class="dropdown"><a href="">报表3<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${deviceClassReportList}">
                            <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title=report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                        </c:forEach>
                    </ul>
                </li>

                <li id="schedulingReport" class="dropdown"><a href="">报表4<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${schedulingReportList}">
                            <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title=report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                        </c:forEach>
                    </ul>
                </li>

                <li id="financeReport" class="dropdown"><a href="">报表5<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach var="rptNameMap" items="${financeReportList}">
                        <li><a href="${pageContext.servletContext.contextPath}/frameset?__report=${rptNameMap.key}.rptdesign&__title=report&user_id=${userId}" target="myreport">${rptNameMap.value}</a></li>
                    </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</div>