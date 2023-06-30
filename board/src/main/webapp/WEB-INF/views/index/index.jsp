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
	<figure class="highcharts-figure">
	    <div id="container"></div>
	    <p class="highcharts-description">
	    </p>
	</figure>
		
</div>
<script>
	let jsonFile = ${chartJson };
	
	let obj = JSON.stringify(jsonFile);
	let obj_ = JSON.parse(obj);
	
	console.log(jsonFile);
	console.log(obj);
	console.log(obj_);
	
	let keys = Object.keys(obj_);
	for(let i = 0; i < keys.length; i++){
		let key = keys[i];
		console.log(key + " " + obj_[key])
	}

	Highcharts.chart('container', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '게시판 별 업로드 된 글의 갯수'
	    },
	    xAxis: {
	        categories: ['6/21', '6/22', '6/23', '6/24', '6/25']
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
	        data: [4, 4, obj_[1], 4, 4]
	    }, {
	        name: '스포츠 게시판',
	        data: [0, 4, obj_[2], 2, 3]
	    }, {
	        name: '게임 게시판',
	        data: [1, 2, obj_[3], 1, 2]
	    }]
	});
</script>
