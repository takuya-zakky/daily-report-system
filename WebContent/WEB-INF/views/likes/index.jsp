<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>フォロー</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="other" items="${others}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td><c:out value="${other.code}" /></td>
                            <td><c:out value="${other.name}" /></td>
                            <c:set var="like_flag" value="0" />
                            <c:forEach var="like" items="${likes}">
                                <c:if test="${other.id == like.employee2.id}">
                                    <c:set var="like_flag" value="1" />
                                </c:if>
                            </c:forEach>
                            <td><c:if test="${ like_flag == 1}">フォロー中</c:if> <c:if
                                    test="${ like_flag == 0}">未フォロー</c:if>

                            <td>
                            <c:if test="${like_flag == 1}">
                                <form method="POST" action="<c:url value='/likes/destroy' />" name="${status.index}">
                                <input type="hidden" name="id" value=<c:out value="${other.id}" />>
                                <input type="hidden" name="_token" value="${_token}" />
                                <p><a href="#" onclick="confirmUnFollow(${status.index});">フォローを解除する</a></p>
                                <script>
                                    function confirmUnFollow(form_number) {
                                        if (confirm("フォローを解除してよろしいでしょうか？")) {
                                            document.forms[form_number].submit();
                                        }
                                    }
                                </script>
                                </form>
                            </c:if>

                            <c:if test="${like_flag == 0}">
                                <form method="POST" action="<c:url value='/likes/create' />" name="${status.index}">
                                <input type="hidden" name="id" value=<c:out value="${other.id}" />>
                                <input type="hidden" name="_token" value="${_token}" />
                                <p><a href="#" onclick="confirmFollow(${status.index});">フォローする</a></p>
                                <script>
                                function confirmFollow(form_number) {
                                    if (confirm("フォローしてよろしいですか？")) {
                                        document.forms[form_number].submit();
                                    }
                                }
                                </script>
                            </form>
                        </c:if>
                    </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${other_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((other_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/likes/index?page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>