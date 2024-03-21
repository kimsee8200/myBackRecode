<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>
comment: <input type="text" name="comment"/><br>
<button id="sendBtn" type="button">SEND</button>
<button id="modBtn">수정</button>
<h2>Data From Server :</h2>
<div id="commentList"></div>
<div id="replyForm" style="display: none">
    <input type="text" name="replyComment">
    <button id="wriRepBtn" type="button">등록</button>
</div>
<script>
    let bno = 1;

    let showList = function (bno){
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/ch4/comments?bno='+bno,  // 요청 URI
            success : function(result){
               $("#commentList").html(toHTML(result));
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }

    $(document).ready(function(){
        showList(bno);

        $("#sendBtn").click(function(){
            let comment = $("input[name=comment]").val();

            if(comment.trim()=='') {
                alert("댓글을 입력해주세요")
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type:'POST',       // 요청 메서드
                url: '/ch4/comments?bno='+bno,  // 요청 URI
                headers : {"content-type":"application/json"},
                data : JSON.stringify({bno : bno, comment : comment}),
                success : function(result){
                    alert(result)
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            });
        });

        $("#modBtn").click(function(){
            let cno = $(this).attr("data-cno")
            let comment = $("input[name=comment]").val();

            if(comment.trim()=='') {
                alert("수정할 내용을 입력해주세요")
                $("input[name=comment]").focus();
                return;
            }

            $.ajax({
                type:'PATCH',       // 요청 메서드
                url: '/ch4/comments/'+cno,  // 요청 URI
                headers : {"content-type":"application/json"},
                data : JSON.stringify({cno : cno, comment : comment}),
                success : function(result){
                    alert(result)
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            });
        });

        $("#commentList").on("click",".modBtn",function(){
            let cno = $(this).parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent()).text()

            $("input[name=comment]").val(comment);

            $("#modBtn").attr("data-cno",cno);
        });

        //$("#delBtn").click(function(){ 비동기 처리 때문에 이렇게 하면 안됨.
        $("#commentList").on("click",".delBtn",function(){
            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");

            console.log(cno)
            console.log(bno)

            $.ajax({
                type:'DELETE',       // 요청 메서드
                url: '/ch4/comments/'+cno+'?bno='+bno,  // 요청 URI
                success : function(result){
                    alert(result)
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            });
        });



        $("#commentList").on("click",".replyBtn",function(){
            $("#replyForm").appendTo($(this).parent());

            $("#replyForm").css("display","block");
        });

        $("#wriRepBtn").click(function (){
            let comment = $("input[name=replyComment]").val();
            let pcno = $("#replyForm").parent().attr("data-pcno");

            console.log(pcno)

            if(comment.trim()=='') {
                alert("댓글을 입력해주세요")
                $("input[name=replyComment]").focus();
                return;
            }

            $.ajax({
                type:'POST',       // 요청 메서드
                url: '/ch4/comments?bno='+bno,  // 요청 URI
                headers : {"content-type":"application/json"},
                data : JSON.stringify({bno : bno, pcno : pcno, comment : comment}),
                success : function(result){
                    alert(result)
                    showList(bno);
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            });


            $("#replyForm").css("display", "none")
            $("input[name=replyComment]").val('')
            $('#replyForm').appendTo("body")
        });
    });


    let toHTML = function(comments){
        let tmp = "<ul>";

        comments.forEach(function (comment){
            let tmp2 = '<li data-cno ='+ comment.cno
            tmp2 += ' data-pcno ='+ comment.pcno
            tmp2 += ' data-bno ='+ comment.bno+'>'
            if(comment.cno!=comment.pcno)
                tmp2+='ㄴ'
            tmp2 += ' commenter = <span class="commenter">'+ comment.commenter + '</span>'
            tmp2 += ' comment = <span class="comment">'+ comment.comment + '</span>'
            tmp2 += ' up_date='+comment.up_date
            tmp2 += '<button class="delBtn">삭제</button>'
            tmp2 += '<button class="modBtn">수정</button>'
            tmp2 += '<button class="replyBtn">답글</button>'
            tmp2 += '</li>'
            tmp += tmp2;
        })

        return tmp + '</ul>'
    }
</script>
</body>
</html>