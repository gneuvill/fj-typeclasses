package fj.data;

import fj.F;
import org.derive4j.hkt.__;

public interface Newtype<T, A> {

  A unwrap(T t);

  T wrap(A a);

  default F<T, A> un(F<A, T> __) {
    return this::unwrap;
  }

  default <f, S, B> __<f, A> ala(Functor<f> F, Newtype<S, B> N
    , F<A, T> __
    , F<F<B, S>, __<f, T>> f) {
    return F.map(this::unwrap, f.f(N::wrap));
  }

  default <f, g, S, B> __<g, B> alaF(Functor<f> Ff, Functor<g> Fg, Newtype<S, B> N
    , F<A, T> __
    , F<__<f, T>, __<g, S>> f
    , __<f, A> p) {
    return Fg.map(N::unwrap, f.f(Ff.map(this::wrap, p)));
  }

}
