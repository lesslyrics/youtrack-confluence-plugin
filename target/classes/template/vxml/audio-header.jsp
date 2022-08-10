<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- audio - VXML audio
  --
  --
  -- Required Parameters:
  --
  --
  -- Optional Parameters:
  --
  -- * persona: persona (directory name of persona)
  -- * src: source of audio (under persona directory if persona is specified)
  -- * tts: alternate text
  -- * expr: expr to evaluate on client-side to resolve src
  -- * fetchhint: fetchhint attribute (safe or prefetch)
  -- * fetchtimeout: fetchtimeout attribute 
  -- * maxage: maxage attribute
  -- * maxstale: maxstale attribute
  --
  --%>

<audio 
      <webwork:if test="parameters['persona']!=null && parameters['src']!=null">
         src="<webwork:property value="parameters['persona']"/>/<webwork:property value="parameters['src']"/>"
      </webwork:if>
      <webwork:if test="parameters['persona']==null && parameters['src']!=null">
         src="<webwork:property value="parameters['src']"/>"
      </webwork:if>
      <webwork:if test="parameters['src']==null && parameters['expr']!=null">
         expr="<webwork:property value="parameters['expr']"/>"
      </webwork:if>
      <webwork:if test="parameters['fetchhint']!=null">
         fetchhint="<webwork:property value="parameters['fetchhint']"/>"
      </webwork:if>
      <webwork:if test="parameters['fetchtimeout']!=null">
         fetchtimeout="<webwork:property value="parameters['fetchtimeout']"/>"
      </webwork:if>
      <webwork:if test="parameters['maxage']!=null">
         maxage="<webwork:property value="parameters['maxage']"/>"
      </webwork:if>
      <webwork:if test="parameters['maxstale']!=null">
         maxstale="<webwork:property value="parameters['maxstale']"/>"
      </webwork:if>
><webwork:if test="parameters['tts']!=null"><webwork:property value="parameters['tts']"/></webwork:if>
