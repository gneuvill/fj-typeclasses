package fj.typeclasses.control;

import fj.*;
import fj.typeclasses.data.Functor;
import org.derive4j.hkt.__;

import static fj.Function.*;

public interface Apply<f> extends Functor<f> {

  <A, B> __<f, B> apply(__<f, F<A, B>> fab, __<f, A> fa);

  default <A, B> __<f, A> apFst(__<f, A> fa, __<f, B> fb) {
    return apply(map(constant(), fa), fb);
  }

  default <A, B> __<f, B> apSnd(__<f, A> fa, __<f, B> fb) {
    return apply(map(constant(identity()), fa), fb);
  }

  default <A, B, C> F2<__<f, A>, __<f, B>, __<f, C>> lift2(F2<A, B, C> f) {
    return (fa, fb) -> apply(map(curry(f), fa), fb);
  }

  default <A, B, C, D> F3<__<f, A>, __<f, B>, __<f, C>, __<f, D>> lift3(F3<A, B, C, D> f) {
    return (fa, fb, fc) -> apply(apply(map(curry(f), fa), fb), fc);
  }

  default <A, B, C, D, E> F4<__<f, A>, __<f, B>, __<f, C>, __<f, D>, __<f, E>> lift4(F4<A, B, C, D, E> f) {
    return (fa, fb, fc, fd) -> apply(apply(apply(map(curry(f), fa), fb), fc), fd);
  }

  default <A, B, C, D, E, F$> F5<__<f, A>, __<f, B>, __<f, C>, __<f, D>, __<f, E>, __<f, F$>> lift5(F5<A, B, C, D, E, F$> f) {
    return (fa, fb, fc, fd, fe) -> apply(apply(apply(apply(map(curry(f), fa), fb), fc), fd), fe);
  }

//    interface Infix<f, A, B> {
//        __<f, F<A, B>> get(Apply<f> A);
//
//        default Infix<f, A, B> ᐸᚕᐳ(__<f, A> fb) {
//            return A -> A.apply(get(A), fb);
//        }
//
//        interface Builder<f, A> {
//            __<f, A> get(Apply<f> A);
//
//            default <B, C> Builder<f, F<B, C>> ᐸᚕᐳ(__<f, B> fb) {
//                return A -> A.apply(, get(A));
//            }
//        }
//    }
}
