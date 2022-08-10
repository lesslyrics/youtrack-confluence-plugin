<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- grammar - SRGF XML grammar
  --
  -- 
  -- Required Parameters:
  --
  -- * name: name of the grammar
  --
  -- Optional Parameters:
  --
  -- * weight: weight for the grammar, default is "1.0"
  -- * xml:lang: language of the grammar ,default is "en-US"
  -- * mode: either voice or dtmf, default is "voice"
  -- * model: must point to a List so that an iterator can be used. the value must have a value attribute and optionally a tag attribute
  --
  --%>

<grammar version="1.0"  
         type="application/grammar+xml"
         root="<webwork:property value="parameters['name']"/>"
         weight="<webwork:if test="parameters['weight']==null">1.0</webwork:if><webwork:else><webwork:property value="parameters['weight']"/></webwork:else>"
         xml:lang="<webwork:if test="parameters['lang']==null">en-US</webwork:if><webwork:else><webwork:property value="parameters['lang']"/></webwork:else>"
         mode="<webwork:if test="parameters['mode']==null">voice</webwork:if><webwork:else><webwork:property value="parameters['mode']"/></webwork:else>"
>
   <rule name="<webwork:property value="parameters['name']"/>" scope="public">
	   <webwork:iterator value="parameters['model']">
	      <item <webwork:if test="tag!=null">tag="<webwork:property value="tag"/>"</webwork:if>><webwork:property value="value"/></item>
	   </webwork:iterator>
   </rule>
