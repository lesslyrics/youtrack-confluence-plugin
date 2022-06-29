<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- filled
  --
  --
  -- Required Parameters:
  --
  --
  -- Optional Parameters:
  --
  -- * mode: bargein attribute
  -- * namelist: xml:lang attribute
  -- * action: action url to go to on execution
  --
  --%>

<filled
       <webwork:if test="parameters['mode']!=null">mode="<webwork:property value="parameters['mode']"/>"</webwork:if>
       <webwork:if test="parameters['namelist']!=null">namelist="<webwork:property value="parameters['namelist']"/>"</webwork:if>
>
       <webwork:if test="parameters['action']!=null">
          <log>Executing action: <webwork:property value="parameters['action']"/></log>
          <submit fetchhint="safe" 
                  next="<webwork:property value="parameters['action']"/>"
                  <webwork:if test="parameters['actionaudio']!=null">
                  fetchaudio="<webwork:property value="parameters['actionaudio']"/>"
                  </webwork:if>
          />
       </webwork:if>
