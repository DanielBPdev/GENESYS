<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "form">
        <#if realm.password>
         <section class="content_login">
            <div class="row">
              <div class="col-xs-12 col-md-4 col-md-offset-4 col-sm-8 col-sm-offset-2">
                <fieldset>
                    <form id="kc-form-login" class="form-signin" action="${url.loginAction}" method="post">
                        <h3 class="form-signin-heading"">Usted esta ingresando como Usuario</h3>
                        <legend><h4 class="form-signin-heading" >Ya me encuentro registrado</h4></legend>
                        <div id="error-kc-content"  style="display:none" class="${properties.kcContentClass!}">
                            <div id="kc-content-wrapper" class="${properties.kcContentWrapperClass!}">
                                
                                    <div class="${properties.kcFeedbackAreaClass!}">
                                        <div class="alert alert-error">
                                            <span id="mensajeError" class="kc-feedback-text">Usuario y contraseña incorrecto</span>
                                        </div>
                                    </div>
                                
                            </div>
                        </div>


                        <label for="username" class="sr-only">Usuario</label>                        
                        <input id="username" class="form-control" name="username"  value="${(login.username!'')?html}" type="text" autofocus placeholder="Usuario"  required />
                        <label for="password" class="sr-only">Contraseña</label>
                        <input id="password" class="form-control" name="password" type="password" autocomplete="off" placeholder="Contraseña" required />
                        <label class="pull-right"><a id="redirectUrl" href="http://localhost:8086/#/login/recuperarPassword"><small>Olvidé mi usuario y/o contraseña</small></a></label>
                        <input class="btn btn-block btn-primary center-block" name="login" id="kc-login"  type="submit" value="Entrar"/>
                    </form>
                </fieldset>
              
              </div>
            </div>

        </section>

        <script type="text/javascript">
        var urlFinal='';
            $( document ).ready(function() {
                var urlSession = sessionStorage.getItem("urlFinal");
                if(urlSession!== null){
                    urlFinal=urlSession;
                }else{
                    var url = window.location.href.split("redirect_uri=");
                    var urldeco=decodeURIComponent(url[1]);
                    //var ipServer=/^(.*?\/)(.*?\/)(.*?\/)/.exec(urldeco)[0];

                    var urlArray=urldeco.split("web-application");
                    if(urlArray.length>1){
                        var newUrlArray=urlArray[0]+"web-application-public";    
                    }else{
                        var newUrlArray=urlArray[0];
                    }
                    var newUrlsinFrgament=newUrlArray.split("?")[0];
                    var newUrlsinState=newUrlsinFrgament.split("&")[0];
                    urlFinal=newUrlsinState.split("#")[0];
                    sessionStorage.setItem("urlFinal",urlFinal)
                }
                 
                
                
/*
                console.log("url",url);
                console.log("urldeco",urldeco);
                console.log("urlArray",urlArray);
                console.log("newUrlArray",newUrlArray);
                console.log("urlFinal",urlFinal);*/


                $("#redirectUrl").attr("href", urlFinal+"#/login/recuperarPassword");
            });
        </script>

        </#if>
      

        
    </#if>
</@layout.registrationLayout>
