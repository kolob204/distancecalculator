<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.11.2019
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<html>

  <head>

    <title>JAX-RS: Jersey 2 / JAXB / WindFly</title>
    <link href="css/style.css" rel="stylesheet">

  </head>

  <body>

  <div class="header">  <h1>REST Application</h1>  </div>

  <p class="copyright">created by <a class="emailme" href="mailto:elastic@yandex.ru" >Viktor Frolov ®</a></p>

  <p>Работа с Mysql</p>
  <a  class="textlink" href="/distance/service/getCities">GET : Список городов (XML Format)</a>
  <br>
  <a  class="textlink" href="/distance/service/getCities2">GET : Список городов (text Format)</a>
  <br>
  <a  class="textlink" href="/distance/service/getCities3">GET : Список городов (output in template)</a>
  <br>
  <a  class="textlink" href="/distance/service/getMatrix">GET : Посмотреть матрицу расстояний в БД (XML Format)</a>
  <br>
  <br>
  <a class="textlink"  href="/distance/service/getDistance/Самара/Москва">Debug: Тест запроса расстояния (Самара - Москва)</a>
  <br>
  <a class="textlink"  href="/distance/service/getDistance/Москва/Самара">Debug: Тест запроса расстояния (Москва - Самара)</a>
  <br>
  <label>PS Запрос в БД осуществляется независимо от порядка передаваемых параметров</label>
  <br>
  <div>
      <div class="search">
      <p><b>Найти дистанцию между двумя городами</b></p>

          <form action="/distance/service/getDistance"  method="post" name="form2">
              <label>Выберите метод поиска данных:</label>
              <br>
              <select name="method">
              <option value="none" hidden="">Выберите метода расчёта расстояни</option>
              <option selected value="1">Данные из БД</option>
              <option value="2">CrowFlight</option>
              <option value="3">Использовать оба метода</option>
              </select>
              <br><br>
              <label>Укажите названия городов:</label>
              <input style="display: block" placeholder="Город отправления" value="Самара"      type="text" name="cityname1">
              <input style="display: block" placeholder="Город назначения"  value="Владивосток" type="text" name="cityname2">
              <button style="display: block" type="submit">Запросить данные...</button>
          </form>
      </div>

    <div class="search">
      <p><b>Найти дистанцию между двумя городами</b><br>
         Альтернативный вариант (предоставление списка городов из базы)
                </p>

      <form action="/distance/service/getDistance"  method="post" name="form3">.
        <label>Выберите метод поиска данных:</label>
        <br>
        <select name="method">
          <option value="none" hidden="">Выберите метода расчёта расстояни</option>
          <option selected value="1">Данные из БД</option>
          <option value="2">CrowFlight</option>
          <option value="3">Использовать оба метода</option>
        </select>

        <br><br>
        <label>Укажите названия первого городов:</label><br>
        <select name="cityname1">
          <c:forEach items="${cities2}" var="city2">
            <option  value="${city2.name}">${city2.name}</option>
          </c:forEach>
        </select>
        <br>
          <select name="cityname2">
          <c:forEach items="${cities2}" var="city2">
            <option value="${city2.name}" >${city2.name}</option>
          </c:forEach>
        </select>
        <button style="display: block" type="submit">Запросить данные...</button>
      </form>
    </div>


  </div>
  <br>

  <div class="add">
    <p><b>Добавить город в Базу Данных</b></p>

    <form method="post" action="/distance/service/addcity">
      <input style="display: block" placeholder="Название города" value=""      type="text" name="cityname">
      <input style="display: block" placeholder="Широта  ХХ.ХХХХХХ"  value="" type="text" name="latitude">
      <input style="display: block" placeholder="Долгота ХХ.ХХХХХХ"  value="" type="text" name="longitude">
      <button style="display: block" type="submit">Внести запись в БД</button>
    </form>
  </div>

  <div>
      <div class="upload">
        <p><b>Загрузить список городов из XML файла</b></p>
        <form action="/distance/service/addcitiesfromfile" method="post" enctype="multipart/form-data">
          <p>Select a file : <input type="file" name="file" size="45" accept=".xml" /></p>
          <input type="submit" value="Upload XML" />
        </form>
      </div>

      <div class="upload">
          <p><b>Загрузить Матрицу расстояний из XML файла</b></p>
          <form action="/distance/service/addmatrixfromfile" method="post" enctype="multipart/form-data">
              <p>Select a file : <input type="file" name="file" size="45" accept=".xml" /></p>
              <input type="submit" value="Upload XML" />
          </form>
      </div>

  </div>
  </body>
</html>
