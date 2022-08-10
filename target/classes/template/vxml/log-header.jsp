<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- log 
  --
  --
  -- Required Parameters:
  --
  --
  -- Optional Parameters:
  --
  -- * expr: expr attribute
  -- * label: label attribute
  --
  --%>

<log 
       <webwork:if test="parameters['label']!=null">label="<webwork:property value="parameters['label']"/>"</webwork:if>
       <webwork:if test="parameters['expr']!=null">expr="<webwork:property value="parameters['expr']"/>"</webwork:if>
>