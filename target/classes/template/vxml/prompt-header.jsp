<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- prompt 
  --
  --
  -- Required Parameters:
  --
  --
  -- Optional Parameters:
  --
  -- * bargein: bargein attribute
  -- * lang: xml:lang attribute
  -- * count: count attribute
  -- * cond: cond attribute
  --
  --%>

<prompt 
       <webwork:if test="parameters['bargein']!=null">bargein="<webwork:property value="parameters['bargein']"/>"</webwork:if>
       <webwork:if test="parameters['lang']!=null">xml:lang="<webwork:property value="parameters['lang']"/>"</webwork:if>
       <webwork:if test="parameters['count']!=null">count="<webwork:property value="parameters['count']"/>"</webwork:if>
       <webwork:if test="parameters['cond']!=null">cond="<webwork:property value="parameters['cond']"/>"</webwork:if>
>