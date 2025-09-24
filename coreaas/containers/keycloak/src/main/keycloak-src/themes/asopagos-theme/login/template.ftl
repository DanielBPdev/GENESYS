<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="${properties.kcHtmlClass!}">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="robots" content="noindex, nofollow">

    <#if properties.meta?has_content>
        <#list properties.meta?split(' ') as meta>
            <meta name="${meta?split('==')[0]}" content="${meta?split('==')[1]}"/>
        </#list>
    </#if>
    <title><#nested "title"></title>
    <link rel="icon" href="${url.resourcesPath}/img/favicon.ico" />

     <script src="${url.resourcesPath}/js/jQuery-2.1.4.min.js"></script>
    <script src="${url.resourcesPath}/js/bootstrap.min.js" ></script>
    
    <link rel="stylesheet" href="${url.resourcesPath}/css/bootstrap.min.css" type="text/css" ></link>
    <link rel="stylesheet" href="${url.resourcesPath}/css/estilosgeneral.min.css" type="text/css" ></link>
    
</head>

<body class="login-pf">
    <div id="kc-logo"><a href="${properties.kcLogoLink!'#'}"><div id="kc-logo-wrapper"></div></a></div>
     
    <div id="kc-container" class="${properties.kcContainerClass!}">
       <#nested "form">    
    </div>
<#if displayMessage && message?has_content>
    <script type="text/javascript">
    $( document ).ready(function() {
        $("#mensajeError").text('${message.summary}');
        $("#error-kc-content").css('display', 'block');
    });
    </script>
</#if>

</body>
</html>
</#macro>
