import { HttpInterceptorFn } from '@angular/common/http';

/**
 * Tüm isteklere withCredentials ekler; böylece Gateway'in HttpOnly session
 * cookie'si her istekte gönderilir/alınır.
 */
export const credentialsInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req.clone({ withCredentials: true }));
};
