package fj.typeclasses.control;

import org.derive4j.hkt.__2;

public interface Category<a> extends Semigroupoid<a> {

  <T> __2<a, T, T> id();
}
