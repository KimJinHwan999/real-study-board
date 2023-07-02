<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h2 class="sub-header">웃으면 복이와요</h2>
    <div class="table-responsive">
    	
        <table class="table table-striped">
            <thead>
            <tr>
                <th>글번호</th>
                <th style="width:50%;">글제목</th>
                <th>작성일자</th>
                <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="firstPost" value="${page.nextPrevPage * 10 - 10 }"/>
            <c:forEach var="findData" items="${page.findPageData }">
            	<c:set var="firstPost" value="${firstPost + 1 }"/>
	            <tr>
	                <td>${firstPost }</td>
	                <td><a href="/board/post/${findData.post_id }">${findData.post_name }</a></td>
	                <td>${findData.post_date }</td>
	                <td>${findData.post_views }</td>
	            </tr>
            </c:forEach>
            
            </tbody>
        </table>
        
    </div>
    <a href="/board/write"><button>글쓰기</button></a>
	
	<!-- 페이징처리 -->
	 <div class="mt-3">
       <nav aria-label="Page navigation example">
       		<ul class="pagination justify-content-end mb-0">
               <li class="page-item">
                   <a class="page-link" href="/${page.nextPrevPage-1}">Previous</a>
               </li>
               
               <c:forEach var="totalPageList" items="${page.totalPageList }" >
	               <li class="page-item">
	               		<a href="/${totalPageList }">${totalPageList }</a>
	               </li>
               </c:forEach>
               
               <li class="page-item">
                   <a class="page-link" href="/${page.nextPrevPage+1}">Next</a>
               </li>
           </ul>
       </nav>
   	</div>
	<input id="chartJson" value=${chartJson } style="display:none;">
	<input id="chartJson2" value=${chartJson2 } style="display:none;">
	<figure class="highcharts-figure">
	    <div id="container"></div>
	    <p class="highcharts-description">
	    </p>
	     <div id="container2"></div>
	    <p class="highcharts-description">
	    </p>
	</figure>
		
</div>
<script>
	let jsonFile = ${chartJson };
	let jsonFile2 = ${chartJson2 };

	Highcharts.chart('container', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '게시판 별 업로드 된 글의 갯수'
	    },
	    xAxis: {
	        categories: [jsonFile[0][4].작성일자, jsonFile[0][3].작성일자, jsonFile[0][2].작성일자, jsonFile[0][1].작성일자, jsonFile[0][0].작성일자]
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '글의 갯수'
	        }
	    },
	    tooltip: {
	        pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
	        shared: true
	    },
	    plotOptions: {
	        column: {
	            stacking: 'percent'
	        }
	    },
	    series: [{
	        name: '유머 게시판',
	        data: [jsonFile[0][4].게시글수, jsonFile[0][3].게시글수, jsonFile[0][2].게시글수, jsonFile[0][1].게시글수, jsonFile[0][0].게시글수]
	    }, {
	        name: '스포츠 게시판',
	        data: [jsonFile[1][4].게시글수, jsonFile[1][3].게시글수, jsonFile[1][2].게시글수, jsonFile[1][1].게시글수, jsonFile[1][0].게시글수]
	    }, {
	        name: '게임 게시판',
	        data: [jsonFile[2][4].게시글수, jsonFile[2][3].게시글수, jsonFile[2][2].게시글수, jsonFile[2][1].게시글수, jsonFile[2][0].게시글수]
	    }]
	});
	
	Highcharts.chart('container2', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '접속왕 랭킹 TOP 7'
	    },
	    subtitle: {
	        text: '접속을 가장 많이 한 회원은?'
	    },
	    xAxis: {
	        type: 'category',
	        labels: {
	            rotation: -45,
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '접속횟수'
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        pointFormat: '로그인 횟수: <b>{point.y:.1f} 회</b>'
	    },
	    series: [{
	        name: 'Population',
	        colors: [
	            '#9b20d9', '#9215ac', '#861ec9', '#7a17e6', '#7010f9', '#691af3',
	            '#6225ed', '#5b30e7', '#533be1', '#4c46db', '#4551d5', '#3e5ccf',
	            '#3667c9', '#2f72c3', '#277dbd', '#1f88b7', '#1693b1', '#0a9eaa',
	            '#03c69b',  '#00f194'
	        ],
	        colorByPoint: true,
	        groupPadding: 0,
	        data: [
	            [jsonFile2[0].회원아이디, jsonFile2[0].로그인횟수],
	            [jsonFile2[1].회원아이디, jsonFile2[1].로그인횟수],
	            [jsonFile2[2].회원아이디, jsonFile2[2].로그인횟수],
	            [jsonFile2[3].회원아이디, jsonFile2[3].로그인횟수],
	            [jsonFile2[4].회원아이디, jsonFile2[4].로그인횟수],
	            [jsonFile2[5].회원아이디, jsonFile2[5].로그인횟수],
	            [jsonFile2[6].회원아이디, jsonFile2[6].로그인횟수],
	        ],
	        dataLabels: {
	            enabled: true,
	            rotation: -90,
	            color: '#FFFFFF',
	            align: 'right',
	            format: '{point.y:.1f}', // one decimal
	            y: 10, // 10 pixels down from the top
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    }]
	});
</script>
