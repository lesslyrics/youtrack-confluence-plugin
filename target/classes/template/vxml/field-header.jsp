<%@ taglib uri="webwork" prefix="webwork" %>

<%--
  -- field - VXML field tag
  --
  --
  -- Required Parameters:
  --
  -- * name: name of the field
  --
  --
  -- Optional Parameters:
  --
  -- * cond: cond attribute
  -- * slot: slot attribute
  -- * modal: modal attribute
  -- * type: type attribute
  --%>

<field name="<webwork:property value="parameters['name']"/>"
       <webwork:if test="parameters['modal']!=null">
           modal="<webwork:property value="parameters['modal']"/>"
       </webwork:if>
       <webwork:if test="parameters['slot']!=null">
           slot="<webwork:property value="parameters['slot']"/>"
       </webwork:if>
       <webwork:if test="parameters['cond']!=null">
           cond="<webwork:property value="parameters['cond']"/>"
       </webwork:if>
       <webwork:if test="parameters['type']!=null">
           type="<webwork:property value="parameters['type']"/>"
       </webwork:if>
>
   <log>Entering field: <webwork:property value="parameters['name']"/></log>
