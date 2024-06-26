import { TableProps } from "antd";
import { debounce } from "lodash";
import { useCallback, useEffect, useState } from "react";

interface IProps {
  className: string;
}

type IScroll<T> = Pick<Partial<TableProps<T>>, "scroll">["scroll"];

function useCalcTableScroll<T>({ className }: IProps) {
  const [scroll, setScroll] = useState<IScroll<T>>({ y: "100%", x: "100%" });

  const calcTableScroll = useCallback((targetTableEle: HTMLElement) => {
    return () => {
      const tableContentHeight = targetTableEle.offsetHeight;
      const tableContentWidth = targetTableEle.offsetWidth;
      const tableHeader =
        targetTableEle.querySelector<HTMLElement>(".ant-table-thead");
      const tableFooter =
        targetTableEle.querySelector<HTMLElement>(".ant-table-footer");

      setScroll({
        y:
          tableContentHeight -
          (tableHeader?.offsetHeight || 0) -
          (tableFooter?.offsetHeight || 0),
        x: tableContentWidth,
      });
    };
  }, []);

  useEffect(() => {
    const targetTableEle = document.querySelector<HTMLElement>(
      stringToSelector(className)
    );

    if (!targetTableEle) return;

    const resizeObserver = new ResizeObserver(
      debounce(calcTableScroll(targetTableEle), 100)
    );

    resizeObserver.observe(targetTableEle);

    return () => {
      resizeObserver.unobserve(targetTableEle);
    };
  }, [calcTableScroll, className]);

  return { scroll };
}

export function stringToSelector(str: string) {
  if (!str) return "";

  str = str.replace(/ /g, ".");

  ["[", "]", "(", ")"].forEach((it) => {
    str = str.replace(new RegExp(`\\${it}`, "g"), `\\${it}`);
  });

  return `.${str}`;
}

export default useCalcTableScroll;
