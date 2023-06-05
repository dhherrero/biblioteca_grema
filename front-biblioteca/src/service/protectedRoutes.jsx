import { useRoute,useLocation } from 'wouter';

export function ProtectedRoute({ component: Component, ...rest }) {
  const [, setLocation] = useLocation()
  const isAuthenticated = !!sessionStorage.getItem("rol"); 
  const [, params] = useRoute(rest.path);
  return isAuthenticated ? <Component params={params} {...rest} /> : setLocation("/login");
}


export function ProtectedRolRoute({ component: Component, ...rest }) {
  const [, setLocation] = useLocation()
  const isAuthenticated = !!sessionStorage.getItem("rol"); 
  const isSuperUser = sessionStorage.getItem("rol")==="superusuario" ? true: false; 

  if (!isAuthenticated) {
    setLocation("/login")
    return
  }

  if (isSuperUser) return <Component {...rest} />

  setLocation("/biblioteca");

}
