# DTO


```
<%@ page import="javax.naming.*, javax.naming.directory.*, java.util.Hashtable" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <script>
        function showAlert(message) {
            alert(message);
        }
    </script>
</head>
<body>

    <h2>Login Page</h2>

    <form method="post">
        Username: <input type="text" name="username" required /><br/><br/>
        Password: <input type="password" name="password" required /><br/><br/>
        <input type="submit" value="Login" />
    </form>

    <%
        // Check if form was submitted
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // LDAP server and authentication information
            String ldapURL = "ldap://your-ldap-server:389"; // Change to your LDAP server URL
            String baseDN = "dc=yourdomain,dc=com";         // Change to your base DN
            String groupDN = "CN=YourGroup,OU=Groups,DC=yourdomain,DC=com";  // Change to your AD group DN

            // LDAP environment setup
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapURL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "yourdomain\\" + username);  // Use domain\username format
            env.put(Context.SECURITY_CREDENTIALS, password);

            try {
                // Try to authenticate the user
                DirContext ctx = new InitialDirContext(env);

                // Search to see if the user belongs to the required group
                String searchFilter = "(&(objectClass=user)(sAMAccountName=" + username + "))";
                SearchControls searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

                NamingEnumeration<SearchResult> results = ctx.search(baseDN, searchFilter, searchControls);
                if (results.hasMore()) {
                    SearchResult result = results.next();
                    Attributes attributes = result.getAttributes();

                    // Check if user is part of the required AD group
                    Attribute memberOf = attributes.get("memberOf");
                    if (memberOf != null && memberOf.contains(groupDN)) {
                        // User is authenticated and belongs to the group
                        response.sendRedirect("success.jsp");
                    } else {
                        // User is authenticated but not part of the group
                        out.println("<script>showAlert('Authentication successful, but you do not belong to the required group.');</script>");
                    }
                } else {
                    // No matching user found in LDAP
                    out.println("<script>showAlert('User not found in LDAP.');</script>");
                }

                ctx.close();
            } catch (AuthenticationException e) {
                // Authentication failed
                out.println("<script>showAlert('Authentication failed. Please check your username and password.');</script>");
            } catch (NamingException e) {
                // LDAP error
                out.println("<script>showAlert('LDAP error occurred: " + e.getMessage() + "');</script>");
            }
        }
    %>

</body>
</html>
```


```
<!DOCTYPE html>
<html>
<head>
    <title>Success</title>
</head>
<body>

    <h2>Login Successful</h2>
    <p>Welcome to the success page!</p>

</body>
</html>

```

mkdir myapp

mkdir -p myapp/WEB-INF
cd /bin
./startup.sh

```
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World JSP</title>
</head>
<body>
    <h1>Hello World from JSP!</h1>
</body>
</html>

```
