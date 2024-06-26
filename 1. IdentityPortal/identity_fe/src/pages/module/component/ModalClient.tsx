import { Button, Form, Input, Modal } from "antd";
import { Client } from "#type/index.t";

export interface ModalClientProps {
  show: boolean;
  onHide: () => void;
  client: Client | undefined;
}

const ModalClient = (props: ModalClientProps) => {
  const footer = (
    <div>
      <Button onClick={props.onHide}>Đóng</Button>
    </div>
  );

  return (
    <Modal open={props.show} onCancel={props.onHide} centered footer={footer}>
      <Form layout='vertical'>
        <Form.Item label='Client ID'>
          <Input value={props?.client?.clientId} readOnly />
        </Form.Item>
        <Form.Item label='Client Secret'>
          <Input value={props?.client?.clientSecret} readOnly />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ModalClient;
