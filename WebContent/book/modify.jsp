<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>书籍</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/formValidation.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/vendor/default.css">
	
</head>
<body>

	<div class="jq22-container">
		<div class="container">
		    <div class="row">
		        <div class="col-lg-8 col-lg-offset-2">
		            <div class="page-header">
		                <h2>修改书籍</h2>
		              	  ${message }
		            </div>

		            <form id="defaultForm" method="post" class="form-horizontal" action="${pageContext.request.contextPath}/book/BookAction?action=modify">
		            	 <div class="form-group">
		                    <label class="col-lg-3 control-label">书的编号</label>
		                    <div class="col-lg-5" style="margin-top:7px; ">
		                       <input type="hidden" class="form-control" name="bid"
									value="${modifyBook.bid }" /> ${modifyBook.bid }
		                    </div>
		                </div>
		                 
		                
		                 <div class="form-group">
		                    <label class="col-lg-3 control-label">书的名字</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="bname" value="${modifyBook.bname }"/>
		                    </div>
		                </div>
		                <div class="form-group">
		                    <label class="col-lg-3 control-label">书的作者</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="bauthor" value="${modifyBook.bauthor }"/>
		                    </div>
		                </div>

		                <div class="form-group">
		                    <label class="col-lg-3 control-label">书的类型</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="btype" value="${modifyBook.btype }"/>
		                    </div>
		                </div>

		                <div class="form-group">
		                    <label class="col-lg-3 control-label">书的价格</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="bprice" value="${modifyBook.bprice }"/>
		                    </div>
		                </div>
		                 <div class="form-group">
		                    <label class="col-lg-3 control-label">出版社</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="bpublisher" value="${modifyBook.bpublisher }"/>
		                    </div>
		                </div>
		                
		                 <div class="form-group">
		                    <label class="col-lg-3 control-label">存储量</label>
		                    <div class="col-lg-5">
		                        <input type="text" class="form-control" name="bstore" value="${modifyBook.bstore }"/>
		                    </div>
		                </div>
		                

		                <div class="form-group">
		                    <div class="col-lg-9 col-lg-offset-3">
		                       	<input type="button" class="btn btn-primary" value="点击返回" onclick="javascript:window.location.href='${pageContext.request.contextPath}/book/BookAction?action=searchUI'"/>
		                        <button type="submit" class="btn btn-primary">确认</button>
		                    </div>
		                </div>
		            </form>
		        </div>
		    </div>
		</div>
		
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/formValidation.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/framework/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/language/zh_CN.js"></script>
    <script type="text/javascript">
$(document).ready(function() {
    $('#defaultForm')
        .formValidation({
            message: 'This value is not valid',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
              
               
               
                bid: {
                    validators: {
                        notEmpty: {
                            message: '书的编号不能为空'
                        }
                    }
                },
                bname: {
                    validators: {
                        notEmpty: {
                            message: '书的名字不能为空'
                        }
                    }
                },
                bauthor: {
                    validators: {
                        notEmpty: {
                            message: '书的作者不能为空'
                        }
                    }
                },
                
                btype: {
                    validators: {
                        notEmpty: {
                            message: '书的类型不能为空'
                        }
                    }
                },
                
                bprice: {
                    validators: {
                        notEmpty: {
                            message: '书的价格不能为空'
                        }
                    }
                },
                bpublisher: {
                    validators: {
                        notEmpty: {
                            message: '出版社不能为空'
                        }
                    }
                },
                bstore: {
                    validators: {
                        notEmpty: {
                            message: '存储量不能为空'
                        }
                    }
                },


            }
        });
});
</script>
</body>
</html>
